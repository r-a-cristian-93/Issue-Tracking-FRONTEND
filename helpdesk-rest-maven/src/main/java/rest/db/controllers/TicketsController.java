package rest.db.controllers;

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
@RequestMapping("ticket")
public class TicketsController {
	private final EntityManager entityManager;
	private final TicketsRepository ticketsRepo;
	private final DepartmentsRepository departmentsRepo;
	private final UsersRepository usersRepo;
	private final StatusRepository statusRepo;
	
	@ResponseBody
	@GetMapping("/find")
	public List<TicketDetailsProjection> find(Integer id, String status, String department) {
		if (status!=null && department==null) {	
			StatusModel statusModel = StatusModel.getInstance(status);
			return ticketsRepo.findByStatus(statusModel);
		}			
		if (status==null && department!=null) {
			DepartmentModel departmentModel = DepartmentModel.getInstance(department);
			return ticketsRepo.findByConcernedDepartment(departmentModel);
		}
		if (status!=null && department!=null) {
			StatusModel statusModel = StatusModel.getInstance(status);
			DepartmentModel departmentModel = DepartmentModel.getInstance(department);
			return ticketsRepo.findByStatusAndConcernedDepartment(statusModel, departmentModel);
		}			
		return ticketsRepo.findById(id, TicketDetailsProjection.class);
	}	
	
	@ResponseBody
	@PostMapping("/add")
	public void insertTicket(
				@RequestParam String summary,
				@RequestParam String issue,
				@RequestParam String concernedDepartment) {		
		DepartmentModel department = departmentsRepo.findByValue(concernedDepartment);
		//get openedBy from User bean from JWT
		UserModel openedBy = usersRepo.getOne(1);
		TicketModel ticket = TicketModel.getInstance();
		ticket.setOpenedBy(openedBy);
		ticket.setSummary(summary);
		ticket.setIssue(issue);	
		ticket.setConcernedDepartment(department);		
		ticketsRepo.save(ticket);
	}	
	
	@ResponseBody
	@PutMapping("/{id}/update")
	public void assignTo(
				@PathVariable Integer id,
				@RequestParam Integer assignTo) {
		UserModel admin = usersRepo.getOne(assignTo);
		TicketModel ticket = ticketsRepo.getOne(id);
		ticket.setAssignedTo(admin);
		ticketsRepo.save(ticket);
	}
	
	@ResponseBody
	@PutMapping("/{id}/close")
	public void closeTicket(
				@PathVariable Integer id,
				@RequestParam String status) {
		//get closedBy from User bean from JWT
		UserModel closedBy = usersRepo.getOne(1);
		StatusModel statusModel = statusRepo.findByValue(status);
		TicketModel ticket = ticketsRepo.getOne(id);
		ticket.setClosedBy(closedBy);
		ticket.setStatus(statusModel);
		ticketsRepo.save(ticket);
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/delete")
	public void deleteTicket(@PathVariable Integer id) {
		TicketModel ticket = ticketsRepo.getOne(id);
		ticketsRepo.delete(ticket);
	}	
}
