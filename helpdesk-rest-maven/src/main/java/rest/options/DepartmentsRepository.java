package rest.options;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentsRepository extends CrudRepository<DepartmentModel, Integer> {
	public DepartmentModel findByValue(String value);

}
