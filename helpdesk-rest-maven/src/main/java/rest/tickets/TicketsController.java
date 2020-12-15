package rest.tickets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Controller
@RequestMapping("tickets")
public class TicketsController {
	@PersistenceContext private EntityManager entityManager;
		
	@ResponseBody
	@GetMapping("/get/filter")
	public List<TicketDetailsModel> getByEx(
		@RequestParam(value = "id", required = false) Integer id,
		@RequestParam(value = "openedByDepartment", required = false) String openedByDepartment,
		@RequestParam(value = "openedBy", required = false) Integer openedBy,
		@RequestParam(value = "assignedTo", required = false) Integer assignedTo,
		@RequestParam(value = "concernedDepartment", required = false) String concernedDepartment,
		@RequestParam(value = "status", required = false) String status ){	
				
		TicketFilterBean filter = new TicketFilterBean()
			.setTicketId(id)
			.setOpenedBy(openedBy)
			.setAssignedTo(assignedTo)
			.setStatus(status)
			.setOpenedByDepartment(openedByDepartment)
			.setConcernedDepartment(concernedDepartment);
		
		String sql = "SELECT t.ID, t.status, t.summary, t.issue, uo.email opened_by, uo.department opened_by_department, ua.email assigned_to, uc.email closed_by "+ 
					"FROM tickets t "+
					"LEFT JOIN users uo ON t.opened_by=uo.ID "+
					"LEFT JOIN users ua ON t.assigned_to=ua.ID "+
					"LEFT JOIN users uc ON t.closed_by=uc.ID " +
					 filter + ";";
		List results = entityManager.createNativeQuery(sql, TicketDetailsModel.class).getResultList();
		return results;
	}
}
