package rest.db.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.db.models.*;
import rest.db.projections.*;

public interface UsersRepository extends JpaRepository<UserModel, Integer> {
	List<UserIdEmailProjection> findByRole(RoleModel role);
	List<UserIdEmailProjection> findByRoleAndDepartment(RoleModel role, DepartmentModel department);
	UserModel findByEmail(String email);
}
