package rest.db.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.db.models.*;

public interface DepartmentsRepository extends CrudRepository<DepartmentModel, Integer> {
	public DepartmentModel findByValue(String value);

}
