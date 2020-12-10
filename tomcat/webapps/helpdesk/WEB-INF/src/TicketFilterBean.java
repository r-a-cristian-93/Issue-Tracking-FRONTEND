import java.util.*;

public class TicketFilterBean {
	private Map<String, Object> restraints = new LinkedHashMap<>();
	
	public TicketFilterBean() {}
		
	public TicketFilterBean setTicketId(int value) {
		restraints.put("t.ID", value);
		return this;
	}	
	public TicketFilterBean setOpenedBy(int value) {
		restraints.put("t.opened_by", value);
		return this;
	}
	public TicketFilterBean setAssignedTo(int value) {
		restraints.put("t.assigned_to", value);
		return this;
	}
	public TicketFilterBean setStatus(String value) {
		if(!value.equals("All")){
			restraints.put("t.status", value);
		}
		return this;
	}
	public TicketFilterBean setOpenedByDepartment(String value) {
		if(!value.equals("All")){
			restraints.put("uo.department", value);
		}
		return this;
	}
	public TicketFilterBean setConcernedDepartment(String value) {
		restraints.put("t.concerned_department", value);
		return this;
	}
	
	public void setRestraints(Map<String, Object> restraints) {
		this.restraints = restraints;
	}
	
	public Map<String, Object> getRestraints() {
		return restraints;
	}
	
	@Override
	public String toString() {
		StringBuilder filter = new StringBuilder("WHERE 1=1");
		
		restraints.forEach((k,v) -> {
			filter.append(" AND " + k + "=?");
		});	
		return filter.toString();
	}	
}
