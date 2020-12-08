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
		String issue = request.getParameter("issue");
		if(issue!=null && summary!=null && concernedDepartment!=null) {
			try {
				String sql = "INSERT INTO tickets(opened_by, summary, issue, concerned_department) VALUES(?, ?, ?, ?);";
				PreparedStatement prepStatement = Helper.getPreparedStatement(sql);
				prepStatement.setInt(1, userId);
				prepStatement.setString(2, summary);
				prepStatement.setString(3, issue);
				prepStatement.setString(4, concernedDepartment);
				prepStatement.executeUpdate();
				response.sendRedirect(request.getContextPath()+"/user/new-ticket-created.html");
			}
			catch (SQLException e) {
				response.sendError(500, "Server Error");
				e.printStackTrace();
			}		
		}
		else {
			response.sendError(400, "Invalid request");
		}
	}	
}
