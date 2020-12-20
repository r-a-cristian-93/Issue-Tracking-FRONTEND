package rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class LoginController {

	@ResponseBody 
	@GetMapping("/happy")
	public String loginOk() {
		return "you have loged in";
	} 
	
	@ResponseBody    
	@GetMapping("/sad")
	public String loginNok() {
		return "what's wrong?";
	}	
}
