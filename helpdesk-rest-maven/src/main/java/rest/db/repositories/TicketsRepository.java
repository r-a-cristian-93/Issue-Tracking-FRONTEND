package rest.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import rest.db.models.*;
import rest.db.projections.*;

public interface TicketsRepository extends JpaRepository<TicketModel, Integer> {
	<T> List<T> findById(Integer id, Class<T> type);
	List findByStatus(StatusModel status);
	List findByConcernedDepartment(DepartmentModel department);
	List findByStatusAndConcernedDepartment(StatusModel status, DepartmentModel department);
	
	List<TicketDetailsProjection> findByOpenedBy(UserModel openedBy);
	List<TicketDetailsProjection> findByOpenedByAndStatus(UserModel openedBy, StatusModel status);
	
}
