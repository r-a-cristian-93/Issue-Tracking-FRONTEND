package rest.db.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.*;
import rest.db.models.*;

@Entity
@Table(name="tickets")
@Getter @Setter
@DynamicInsert
@DynamicUpdate
public class TicketModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	@Column(name="summary")
	private String summary;
	@Column(name="issue")
	private String issue;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusModel status;
	@ManyToOne
	@JoinColumn(name="opened_by")
	private UserModel openedBy;	
	@ManyToOne
	@JoinColumn(name="assigned_to")
	private UserModel assignedTo;
	@ManyToOne
	@JoinColumn(name="closed_by")
	private UserModel closedBy;
	@ManyToOne
	@JoinColumn(name = "concerned_department")
	private DepartmentModel concernedDepartment;
	
	protected TicketModel() {}
	
	public static TicketModel getInstance() {
		return new TicketModel();
	}
	
}
