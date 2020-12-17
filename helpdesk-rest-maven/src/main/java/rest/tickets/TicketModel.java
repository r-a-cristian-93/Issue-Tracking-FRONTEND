package rest.tickets;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.springframework.context.annotation.Scope;
import lombok.*;
import rest.options.DepartmentModel;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="tickets")
@Getter @Setter
@DynamicInsert
@DynamicUpdate
public class TicketModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="summary")
	private String summary;
	@Column(name="issue")
	private String issue;
	@Column(name="status")
	private String status;
	@Column(name="opened_by")
	private Integer openedBy;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "concerned_department")
	private DepartmentModel concernedDepartment;
	@Column(name="assigned_to")
	private Integer assignedTo;
	@Column(name="closed_by")
	private Integer closedBy;
	
	protected TicketModel() {}
	
	public static TicketModel getInstance() {
		return new TicketModel();
	}
	
}
