public class TicketBean {
	private int id;
	private String openedBy;
	private String closedBy;
	private String assignedTo;
	private String status;
	private String issue;
	private String summary;
	private String openedByDepartment; //openedByDepartment
	//private String concerndeDepartment
	
	public TicketBean() {}
	
	public TicketBean setId(int id) {
		this.id=id;
		return this;
	}
	public TicketBean setOpenedBy(String openedBy) {
		this.openedBy=openedBy;
		return this;
	}
	public TicketBean setClosedBy(String closedBy) {
		this.closedBy=closedBy;
		return this;
	}
	public TicketBean setAssignedTo(String assignedTo) {
		this.assignedTo=assignedTo;
		return this;
	}
	public TicketBean setStatus(String status) {
		this.status=status;
		return this;
	}
	public TicketBean setIssue(String issue) {
		this.issue=issue;
		return this;
	}
	public TicketBean setSummary(String summary) {
		this.summary=summary;
		return this;
	}
	public TicketBean setOpenedByDepartment(String openedByDepartment) {
		this.openedByDepartment=openedByDepartment;
		return this;
	}
	
	public int getId() {
		return id;
	}
	public String getOpenedBy() {
		return openedBy;
	}
	public String getClosedBy() {
		return closedBy;
	}
	public String getStatus() {
		return status;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public String getIssue() {
		return issue;
	}
	public String getSummary() {
		return summary;
	}
	public String getOpenedByDepartment() {
		return openedByDepartment;
	}
}
