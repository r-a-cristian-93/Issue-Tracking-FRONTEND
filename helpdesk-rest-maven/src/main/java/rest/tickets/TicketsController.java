package rest.tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Example;

@Controller
@RequestMapping("tickets")
public class TicketsController {
	@Autowired private TicketsDetailsRepository ticketsDetailsRepo;
		
	@ResponseBody
	@GetMapping("/get/ex")
	public Iterable<TicketDetailsModel> getByEx(
		@RequestParam("status") String status
	){
		System.out.println(status);
		TicketDetailsModel ex = TicketDetailsModel.builder().status(status).build();
		
		System.out.println(ex.getStatus());
		return ticketsDetailsRepo.findAll(Example.of(ex));
	}
}
