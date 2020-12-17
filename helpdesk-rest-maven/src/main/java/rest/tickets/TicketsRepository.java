package rest.tickets;

import org.springframework.data.repository.CrudRepository;

public interface TicketsRepository extends CrudRepository<TicketModel, Integer> {
	
}
