package rest.db.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import lombok.*;
import rest.db.models.*;
import rest.db.projections.*;
import rest.db.repositories.*;

@RestController
@AllArgsConstructor
@RequestMapping("tickets")
public class TicketsController {
	private final EntityManager entityManager;
	private final TicketsRepository ticketsRepo;
	private final DepartmentsRepository departmentsRepo;
	private final UsersRepository usersRepo;
	private final StatusRepository statusRepo;
	
	@ResponseBody
	@GetMapping("/mytickets") 
	public List<TicketDetailsProjection> getMyTickets(String status) {		
		UserModel userModel = getUserFromContext();
		if(status!=null) {
			StatusModel statusModel = StatusModel.getInstance(status);
			return ticketsRepo.findByOpenedByAndStatus(userModel, statusModel);
		} 
		else return ticketsRepo.findByOpenedBy(userModel);
	}
		
	@ResponseBody
	@PostMapping("/add")
	public void insertTicket(
				@RequestParam String summary,
				@RequestParam String issue,
				@RequestParam String concernedDepartment) {		
		DepartmentModel department = departmentsRepo.findByValue(concernedDepartment);
		UserModel openedBy = getUserFromContext();
		TicketModel ticket = TicketModel.getInstance();
		ticket.setOpenedBy(openedBy);
		ticket.setSummary(summary);
		ticket.setIssue(issue);	
		ticket.setConcernedDepartment(department);		
		ticketsRepo.save(ticket);
	}	
	
	@ResponseBody
	@PutMapping("/{id}/update")
	public TicketDetailsProjection assignTo(
				@PathVariable Integer id,
				@RequestParam Integer assignTo) {
		UserModel admin = usersRepo.getOne(assignTo);
		TicketModel ticket = ticketsRepo.getOne(id);
		ticket.setAssignedTo(admin);
		ticketsRepo.save(ticket);
		return ticketsRepo.findById(id, TicketDetailsProjection.class).get(0);
	}
	
	@ResponseBody
	@PutMapping("/{id}/close")
	public TicketDetailsProjection closeTicket(
				@PathVariable Integer id,
				@RequestParam String status) {
		//get closedBy from User bean from JWT
		UserModel closedBy = usersRepo.getOne(1);
		StatusModel statusModel = statusRepo.findByValue(status);
		TicketModel ticket = ticketsRepo.getOne(id);
		ticket.setClosedBy(closedBy);
		ticket.setStatus(statusModel);
		ticketsRepo.save(ticket);
		return ticketsRepo.findById(id, TicketDetailsProjection.class).get(0);
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/delete")
	public void deleteTicket(@PathVariable Integer id) {
		TicketModel ticket = ticketsRepo.getOne(id);
		ticketsRepo.delete(ticket);
	}	



	private UserModel getUserFromContext() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepo.findByEmail(email);
	}
}
