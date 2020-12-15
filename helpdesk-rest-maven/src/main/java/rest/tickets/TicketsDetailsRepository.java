package rest.tickets;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface TicketsDetailsRepository extends JpaRepository<TicketDetailsModel, Integer> {
	@Query(value="SELECT t.ID, t.status, t.summary, t.issue, uo.email opened_by, uo.department opened_by_department, ua.email assigned_to, uc.email closed_by "+ 
					"FROM tickets t "+
					"LEFT JOIN users uo ON t.opened_by=uo.ID "+
					"LEFT JOIN users ua ON t.assigned_to=ua.ID "+
					"LEFT JOIN users uc ON t.closed_by=uc.ID ",
			nativeQuery=true)
	@Override	
	<S extends TicketDetailsModel> List<S> findAll(Example<S> example);
}
