/*
String sql = "SELECT t.ID, t.status, t.summary, t.issue, uo.email opened_by, uo.department opened_by_department, ua.email assigned_to, uc.email closed_by "+ 
					"FROM tickets t "+
					"LEFT JOIN users uo ON t.opened_by=uo.ID "+
					"LEFT JOIN users ua ON t.assigned_to=ua.ID "+
					"LEFT JOIN users uc ON t.closed_by=uc.ID "+
					ticketFilter + ";";	*/
					
package rest.tickets;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name="tickets")
@Getter @Setter
public class TicketDetailsModel {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	@Column(name="status")
	private String status;
	@Column(name="summary")
	private String summary;
	@Column(name="issue")
	private String issue;
	@Column(name="opened_by")
	private String openedBy;
	@Column(name="opened_by_department")
	private String openedByDepartment;
	@Column(name="assigned_to")
	private String assignedTo;
	@Column(name="closed_by")
	private String closedBy;
	
	protected TicketDetailsModel() {}
}
