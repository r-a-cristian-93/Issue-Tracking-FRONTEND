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
