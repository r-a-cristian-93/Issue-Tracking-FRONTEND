package rest.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("options")
public class OptionsController {
	@Autowired private DepartmentsRepository departmentsRepo;
	@Autowired private RolesRepository rolesRepo;
	@Autowired private StatusRepository statusRepo;
	@Autowired private AdminsRepository adminsRepo;	

	@ResponseBody 
	@GetMapping("/get/departments")
	public Iterable<DepartmentModel> getDepartments() {
		return departmentsRepo.findAll();
	} 
	@ResponseBody    
	@GetMapping("/get/roles")
	public Iterable<RoleModel> getRoles() {
		return rolesRepo.findAll();
	}	
	@ResponseBody 
	@GetMapping("/get/status")
	public Iterable<StatusModel> getStatus() {
		return statusRepo.findAll();
	}		
	@ResponseBody
	@GetMapping("/get/admins")	
	public Iterable<AdminModel> getAdmins() {	
		//if User.role=="moderator"  findByDepartment(User.department)
		//if User.role=="owner"      findAll			
		return adminsRepo.findAll();
	}	
	//to be removed
	@ResponseBody
	@GetMapping("/get/admins/{department}")
	public Iterable<AdminModel> getAdminsByDepartment(@PathVariable String department) {
		return adminsRepo.findByDepartment(department);
	}   
}
