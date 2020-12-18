package rest.db.repositories;

import org.springframework.data.repository.CrudRepository;
import rest.db.models.*;

public interface RolesRepository extends CrudRepository<RoleModel, Integer> {
	public RoleModel findByValue(String value);
}
