package rest.db.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.db.models.*;
import rest.db.projections.*;


public interface TicketDetailsProjection {
	Integer getId();	
	String getSummary();
	String getIssue();
	StatusModel getStatus();
	@JsonIgnore
	DepartmentModel getConcernedDepartment();
	UserIdEmailProjection getOpenedBy();
	UserIdEmailProjection getAssignedTo();
	UserIdEmailProjection getClosedBy();	
	
	default DepartmentModel getOpenedByDepartment() {
		return getOpenedBy().getDepartment();
	}
}
