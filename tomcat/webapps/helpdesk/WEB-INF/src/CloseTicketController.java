import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.json.*;
import java.sql.*;

@WebServlet(value="/admin/closeticket")
public class CloseTicketController extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int ticketId = Integer.valueOf(request.getParameter("ticketId"));
		String status = request.getParameter("status");
		int closedBy = ((UserBean) request.getAttribute("user_bean")).getId();
		
		String sql = "UPDATE tickets SET status=?, closed_by=? WHERE ID=?;";
		try {
			PreparedStatement prepStatement = Helper.getPreparedStatement(sql);
			prepStatement.setString(1, status);
			prepStatement.setInt(2, closedBy);
			prepStatement.setInt(3, ticketId);
			prepStatement.executeUpdate();
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}
	}
}
