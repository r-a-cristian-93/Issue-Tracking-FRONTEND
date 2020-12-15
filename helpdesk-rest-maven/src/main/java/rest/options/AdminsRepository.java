package rest.options;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminsRepository extends CrudRepository<AdminModel, Integer> {
	@Query(value = "SELECT * FROM users WHERE role='Admin'", nativeQuery = true)
	Iterable<AdminModel> findAll();
	
	@Query(value = "SELECT * FROM users WHERE role='Admin' AND department=?1", nativeQuery = true)
	Iterable<AdminModel> findByDepartment(String department);	
}
