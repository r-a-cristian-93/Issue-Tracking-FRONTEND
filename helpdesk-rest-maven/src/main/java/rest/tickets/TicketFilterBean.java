package rest.tickets;

import java.util.*;

public class TicketFilterBean {
	private Map<String, Object> restraints = new LinkedHashMap<>();
	
	public TicketFilterBean() {}
		
	public TicketFilterBean setTicketId(Integer value) {
		if (value != null) restraints.put("t.ID", value);
		return this;
	}	
	public TicketFilterBean setOpenedBy(Integer value) {
		if (value != null) restraints.put("t.opened_by", value);
		return this;
	}
	public TicketFilterBean setAssignedTo(Integer value) {
		if (value != null) restraints.put("t.assigned_to", value);
		return this;
	}
	public TicketFilterBean setStatus(String value) {
		if (value != null) {
			if(!value.equals("All")){
				restraints.put("t.status", value);
			}
		}
		return this;
	}
	public TicketFilterBean setOpenedByDepartment(String value) {
		if (value != null) {
			if(!value.equals("All")){
				restraints.put("uo.department", value);
			}
		}
		return this;
	}
	public TicketFilterBean setConcernedDepartment(String value) {
		if (value != null) restraints.put("t.concerned_department", value);
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
			if (v instanceof String) {
				filter.append(" AND " + k + "='" + v + "'");
			}
			else {
				filter.append(" AND " + k + "=" + v);
			}
		});	
		return filter.toString();
	}	
}
