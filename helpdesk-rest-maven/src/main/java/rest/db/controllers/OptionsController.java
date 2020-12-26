package rest.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;

import rest.db.models.*;
import rest.db.repositories.*;
import rest.db.projections.*;
import java.util.*;

import static rest.ApplicationConstants.*;

@Controller
@RequestMapping("options")
public class OptionsController {
	@Autowired private DepartmentsRepository departmentsRepo;
	@Autowired private RolesRepository rolesRepo;
	@Autowired private StatusRepository statusRepo;
	@Autowired private UsersRepository usersRepo;	
		
	@ResponseBody 
	@GetMapping("/departments")
	public Iterable<DepartmentModel> getDepartments() {
		return departmentsRepo.findAll();
	} 
	
	@ResponseBody    
	@GetMapping("/roles")
	public Iterable<RoleModel> getRoles() {
		return rolesRepo.findAll();
	}	
	
	@ResponseBody 
	@GetMapping("/status")
	public Iterable<StatusModel> getStatus() {
		return statusRepo.findAll();
	}	
		
	@ResponseBody
	@GetMapping("/admins")	
	@PreAuthorize("hasAnyAuthority(T(rest.ApplicationConstants).ROLE_MODERATOR, T(rest.ApplicationConstants).ROLE_OWNER)")
	public List<UserIdEmailProjection> getAdmins() {
		UserModel userModel = getUserFromContext();		
		RoleModel roleModel = RoleModel.getInstance(ROLE_ADMIN);
		if(!userModel.getRole().getValue().equals(ROLE_OWNER)) {
			return usersRepo.findByRoleAndDepartment(roleModel, userModel.getDepartment());
		}		
		return usersRepo.findByRole(roleModel);
	}		
	
	private UserModel getUserFromContext() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepo.findByEmail(email);
	} 
}
