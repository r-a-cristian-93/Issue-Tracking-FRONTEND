package rest.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("options")
public class OptionsController {
	@Autowired	private DepartmentsRepository departmentsRepo;
	@Autowired	private RolesRepository rolesRepo;
	@Autowired	private StatusRepository statusRepo;

	@GetMapping(path="/getDepartments")
	public @ResponseBody Iterable<DepartmentModel> getDepartments() {		
		Iterable<DepartmentModel> dep = departmentsRepo.findAll();
		return dep;
	}    
	@GetMapping(path="/getRoles")
	public @ResponseBody Iterable<RoleModel> getRoles() {		
		Iterable<RoleModel> roles = rolesRepo.findAll();
		return roles;
	}    
	@GetMapping(path="/getStatus")
	public @ResponseBody Iterable<StatusModel> getStatus() {		
		Iterable<StatusModel> status = statusRepo.findAll();
		return status;
	}    
}
