package rest.db.projections;

import rest.db.models.*;

public interface UserIdEmailProjection {
	Integer getId();
	String getEmail();
	DepartmentModel getDepartment();
}
