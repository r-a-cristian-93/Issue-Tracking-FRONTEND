package rest.tickets;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.repository.query.Param;


public interface TicketsDetailsRepository extends JpaRepository<TicketDetailsModel, Integer> {
	
}
