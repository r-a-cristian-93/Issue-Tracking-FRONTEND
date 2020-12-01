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
			String sql = "DELETE FROM tickets WHERE ID="+ticketId+";";
			Helper.sqlUpdate(sql);
		}
		catch (SQLException e) {}		
	}
}
