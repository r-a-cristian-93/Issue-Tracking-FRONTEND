package rest.db.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
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

import static rest.ApplicationConstants.*;

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
	@GetMapping("/manage")
	public List<TicketDetailsProjection> getTickets(String status, String department) {
		String userRole = getUserFromContext().getRole().getValue();
		switch(userRole) {
			case ROLE_OWNER: return ownerManagedTickets(status, department);
			case ROLE_MODERATOR: return moderatorManagedTickets(status, department);
			case ROLE_ADMIN: return adminManagedTickets(status, department);
			default: return null;
		}
	}

	public List<TicketDetailsProjection> ownerManagedTickets(String status, String department) {
		StatusModel statusModel = StatusModel.getInstance(status);
		DepartmentModel departmentModel = DepartmentModel.getInstance(department);
		switch (decideCase(status, department)) {
			case 1:	return ticketsRepo.findByStatus(statusModel);
			case 2: return ticketsRepo.findByOpenedByDepartment(departmentModel);
			case 3: return ticketsRepo.findByOpenedByDepartmentAndStatus(departmentModel, statusModel);
			case 4: 
			default: return ticketsRepo.findBy();
		}
	}

	public List<TicketDetailsProjection> moderatorManagedTickets(String status, String department) {
		DepartmentModel concernedDepartment= getUserFromContext().getDepartment();		
		StatusModel statusModel = StatusModel.getInstance(status);
		DepartmentModel departmentModel = DepartmentModel.getInstance(department);
		switch (decideCase(status, department)) {
			case 1:	return ticketsRepo.findByConcernedDepartmentAndStatus(concernedDepartment, statusModel);
			case 2: return ticketsRepo.findByConcernedDepartmentAndOpenedByDepartment(concernedDepartment, departmentModel);
			case 3: return ticketsRepo.findByConcernedDepartmentAndOpenedByDepartmentAndStatus(concernedDepartment, departmentModel, statusModel);
			case 4: 
			default: return ticketsRepo.findByConcernedDepartment(concernedDepartment);
		}
	}
	
	public List<TicketDetailsProjection> adminManagedTickets(String status, String department) {
		UserModel assignedTo = getUserFromContext();		
		StatusModel statusModel = StatusModel.getInstance(status);
		DepartmentModel departmentModel = DepartmentModel.getInstance(department);
		switch (decideCase(status, department)) {
			case 1:	return ticketsRepo.findByAssignedToAndStatus(assignedTo, statusModel);
			case 2: return ticketsRepo.findByAssignedToAndOpenedByDepartment(assignedTo, departmentModel);
			case 3: return ticketsRepo.findByAssignedToAndOpenedByDepartmentAndStatus(assignedTo, departmentModel, statusModel);
			case 4: 
			default: return ticketsRepo.findByAssignedTo(assignedTo);
		}
	}	
	
	@ResponseBody
	@GetMapping("/count")
	public List<TicketCountProjection> countTickets() {
		return ticketsRepo.countTickets(getUserFromContext().getId());		
	}
		
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
	@PreAuthorize("hasAnyAuthority(T(rest.ApplicationConstants).ROLE_MODERATOR, T(rest.ApplicationConstants).ROLE_OWNER)")
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
	@PreAuthorize("hasAnyAuthority(T(rest.ApplicationConstants).ROLE_ADMIN, T(rest.ApplicationConstants).ROLE_MODERATOR, T(rest.ApplicationConstants).ROLE_OWNER)")
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
	@PreAuthorize("hasAuthority(ROLE_OWNER)")
	public void deleteTicket(@PathVariable Integer id) {
		TicketModel ticket = ticketsRepo.getOne(id);
		ticketsRepo.delete(ticket);
	}	
	

	private UserModel getUserFromContext() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usersRepo.findByEmail(email);
	}
	
	private int decideCase(Object A, Object B) {
		if (A!=null && B==null) return 1;	//A
		if (A==null && B!=null) return 2;	//A
		if (A!=null && B!=null) return 3;	//both
		return 4;							//none				
	}
}
