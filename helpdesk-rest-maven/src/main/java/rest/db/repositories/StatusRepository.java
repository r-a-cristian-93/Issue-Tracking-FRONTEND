package rest.db.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.db.models.*;

public interface StatusRepository extends CrudRepository<StatusModel, Integer> {
	public StatusModel findByValue(String value);

}
