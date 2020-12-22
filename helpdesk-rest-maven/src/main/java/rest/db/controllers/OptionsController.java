package rest.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
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
	public List<UserIdEmailProjection> getAdmins(String department) {
		RoleModel roleModel = RoleModel.getInstance("admin");
		if (department!=null) {
			DepartmentModel departmentModel = DepartmentModel.getInstance(department);
			return usersRepo.findByRoleAndDepartment(roleModel, departmentModel);
		}
		return usersRepo.findByRole(roleModel);
	}	 
}
