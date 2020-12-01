public class TicketFilterBean {
	private int ticketId = -1;
	private int openedBy = -1;
	private int assignedTo = -1;
	private String status;
	private String openedByDepartment;
	private String concernedDepartment;
	
	public TicketFilterBean() {}
	
	public TicketFilterBean setTicketId(int ticketId) {
		this.ticketId=ticketId;
		return this;
	}	
	public TicketFilterBean setOpenedBy(int openedBy) {
		this.openedBy=openedBy;
		return this;
	}
	public TicketFilterBean setAssignedTo(int assignedTo) {
		this.assignedTo=assignedTo;
		return this;
	}
	public TicketFilterBean setStatus(String status) {
		this.status=status;
		return this;
	}
	public TicketFilterBean setOpenedByDepartment(String openedByDepartment) {
		this.openedByDepartment=openedByDepartment;
		return this;
	}
	public TicketFilterBean setConcernedDepartment(String concernedDepartment) {
		this.concernedDepartment=concernedDepartment;
		return this;
	}
	
	public int getTicketId() {
		return ticketId;
	}	
	public int getOpenedBy() {
		return openedBy;
	}
	public int getAssignedTo() {
		return assignedTo;
	}
	public String getStatus() {
		return status;
	}
	public String getOpenedByDepartment() {
		return openedByDepartment;
	}
	public String getConcernedDepartment() {
		return concernedDepartment;
	}
	
	@Override
	public String toString() {
		String filter = "WHERE 1=1";
		
		if(ticketId>=0) {
			filter+=" AND t.ID="+ticketId;
		}
		if(openedBy>=0) {
			filter+=" AND t.opened_by="+openedBy;
		}
		if(assignedTo>=0) {
			filter+=" AND t.assigned_to="+assignedTo;
		}
		if(status!=null) {
			if(!status.equals("All")) {
				filter+=" AND t.status='"+status+"'";
			}
		}
		if(openedByDepartment!=null) {
			if(!openedByDepartment.equals("All")) {
				filter+=" AND uo.department='"+openedByDepartment+"'";
			}
		}
		if(concernedDepartment!=null) {
			filter+=" AND t.concerned_department='"+concernedDepartment+"'";
		}
		return filter;
	}
	
}
