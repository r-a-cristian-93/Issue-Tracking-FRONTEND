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
		
		String sql = "UPDATE tickets SET status='"+status+"', closed_by="+closedBy+" WHERE ID="+ticketId+";";
		System.out.println(sql);
		try {
			Helper.sqlUpdate(sql);
			response.setContentType("text/html");
			response.getWriter().print("CLOSED");
		}
		catch (SQLException e) {
			response.setContentType("text/html");
			response.getWriter().print(e.getMessage());
		}
	}
}
