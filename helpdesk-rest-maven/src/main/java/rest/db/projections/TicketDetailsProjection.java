package rest.db.projections;

import rest.db.models.*;
import rest.db.projections.*;

public interface TicketDetailsProjection {
	Integer getId();	
	String getSummary();
	String getIssue();
	StatusModel getStatus();
	DepartmentModel getConcernedDepartment();
	UserIdEmailProjection getOpenedBy();
	UserIdEmailProjection getAssignedTo();
	UserIdEmailProjection getClosedBy();	
}
