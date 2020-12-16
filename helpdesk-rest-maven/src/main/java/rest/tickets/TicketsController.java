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
	public List<TicketDetailsModel> getTickets(TicketFilterBean filter) {
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
