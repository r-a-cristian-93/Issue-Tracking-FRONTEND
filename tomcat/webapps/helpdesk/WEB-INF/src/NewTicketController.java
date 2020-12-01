import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;
import org.json.*;

@WebServlet(value="/user/newticket")
public class NewTicketController extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		int userId = (int) ((UserBean) request.getAttribute("user_bean")).getId();
		String concernedDepartment = request.getParameter("concernedDepartment");
		String summary = request.getParameter("summary");
		String issue = Helper.getBody(request);
		JSONArray newTicketStatus = new JSONArray();
		if(issue!=null) {
			try {
				String sql = "INSERT INTO tickets(opened_by, summary, issue, concerned_department) VALUES("+userId+", '"+summary+"', '"+issue+"', '"+concernedDepartment+"');";
				Helper.sqlUpdate(sql);
				newTicketStatus.put("A new ticket was successfully created.<br/> You will be informed about the progress via email.");
			}
			catch (SQLException e) {
				newTicketStatus.put(e.getMessage());
			}		
		}
		else {
			newTicketStatus.put("Invalid request.");
		}
		response.setContentType("application/json");
		response.getWriter().print(newTicketStatus);
	}	
}
