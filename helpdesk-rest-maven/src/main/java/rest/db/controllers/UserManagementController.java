package rest.db.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import rest.db.models.*;
import rest.db.repositories.*;
import rest.db.projections.*;
import java.util.*;
import lombok.*;

@Controller
@AllArgsConstructor
@RequestMapping("usermanagement")
public class UserManagementController {
	private UsersRepository usersRepo;		
	private BCryptPasswordEncoder pwdEncoder;
	
	@ResponseBody
	@PostMapping("/register")
	@PreAuthorize("hasAuthority(T(rest.ApplicationConstants).ROLE_OWNER)")
	public void registerUser(String username, String password, String department, String role ) {
		UserModel userModel = UserModel.getInstance();
		userModel.setEmail(username);
		userModel.setPassword(pwdEncoder.encode(password));
		userModel.setDepartment(DepartmentModel.getInstance(department));
		userModel.setRole(RoleModel.getInstance(role));
		usersRepo.save(userModel);
	}
	
	@ResponseBody
	@GetMapping("/myinfo")
	public Map<String, Object> getMyInfo() {
		UserModel user = getUserFromContext();
		Map<String, Object> info = new LinkedHashMap<>();
		info.put("id", user.getId());
		info.put("email", user.getEmail());
		info.put("role", user.getRole().getValue());
		return info;
	}
	
	private UserModel getUserFromContext() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepo.findByEmail(email);
	}
}
