import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet(value="/owner/deleteticket")
public class DeleteTicketController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int ticketId = Integer.valueOf(request.getParameter("ticketId"));
		
		try {
			String sql = "DELETE FROM tickets WHERE ID=?;";
			PreparedStatement prepStatement = Helper.getPreparedStatement(sql);
			prepStatement.setInt(1, ticketId);
			prepStatement.executeUpdate();
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}		
	}
}
