package rest.db.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.db.models.*;

public interface UserIdEmailProjection {
	Integer getId();
	String getEmail();
	@JsonIgnore
	DepartmentModel getDepartment();
}
