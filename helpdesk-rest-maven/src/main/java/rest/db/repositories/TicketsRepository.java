package rest.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;
import rest.db.models.*;
import rest.db.projections.*;

public interface TicketsRepository extends JpaRepository<TicketModel, Integer> {
	<T> List<T> findById(Integer id, Class<T> type);
	
	@Query(value = "SELECT status, COUNT(*) count from tickets WHERE opened_by=?1 GROUP BY status;", nativeQuery=true)
	List<TicketCountProjection> countTickets(Integer openedBy);

	List<TicketDetailsProjection> findByOpenedBy(UserModel openedBy);
	List<TicketDetailsProjection> findByOpenedByAndStatus(UserModel openedBy, StatusModel status);
	
	//for OWNER
	List<TicketDetailsProjection> findByStatus(StatusModel status);
	List<TicketDetailsProjection> findByOpenedByDepartment(DepartmentModel department);
	List<TicketDetailsProjection> findByOpenedByDepartmentAndStatus(DepartmentModel department, StatusModel status);
	List<TicketDetailsProjection> findBy();

	//for MODERATOR
	List<TicketDetailsProjection> findByConcernedDepartmentAndStatus(DepartmentModel concernedDep, StatusModel status);
	List<TicketDetailsProjection> findByConcernedDepartmentAndOpenedByDepartment(DepartmentModel concernedDep, DepartmentModel openedByDep);
	List<TicketDetailsProjection> findByConcernedDepartmentAndOpenedByDepartmentAndStatus(DepartmentModel concernedDep, DepartmentModel openedByDep, StatusModel status);
	List<TicketDetailsProjection> findByConcernedDepartment(DepartmentModel department);
	
	//for ADMIN
	List<TicketDetailsProjection> findByAssignedToAndStatus(UserModel assignedTo, StatusModel status);
	List<TicketDetailsProjection> findByAssignedToAndOpenedByDepartment(UserModel assignedTo, DepartmentModel department);
	List<TicketDetailsProjection> findByAssignedToAndOpenedByDepartmentAndStatus(UserModel assignedTo, DepartmentModel department, StatusModel status);
	List<TicketDetailsProjection> findByAssignedTo(UserModel assignedTo);
}
