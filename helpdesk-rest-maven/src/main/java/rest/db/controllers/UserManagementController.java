package rest.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
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
@PreAuthorize("hasAuthority(T(rest.ApplicationConstants).ROLE_OWNER)")
public class UserManagementController {
	private UsersRepository usersRepo;		
	private BCryptPasswordEncoder pwdEncoder;
	
	@ResponseBody
	@PostMapping("/register")
	public void registerUser(String username, String password, String department, String role ) {
		if(username!=null && password!=null && department!=null && role!=null) {
			UserModel userModel = UserModel.getInstance();
			userModel.setEmail(username);
			userModel.setPassword(pwdEncoder.encode(password));
			userModel.setDepartment(DepartmentModel.getInstance(department));
			userModel.setRole(RoleModel.getInstance(role));
			usersRepo.save(userModel);
		}
	}		
}
