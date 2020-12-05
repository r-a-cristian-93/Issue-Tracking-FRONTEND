import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/user/my-tickets")
public class MyTicketsController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int userId=((UserBean) request.getAttribute("user_bean")).getId();
		String status = request.getParameter("status");
		
		JSONArray jsonTickets = new JSONArray();
		try {
			TicketFilterBean filter = new TicketFilterBean().setOpenedBy(userId)
															.setStatus(status);
			ArrayList<TicketBean> tickets = Helper.getTickets(filter);
			tickets.forEach(e->jsonTickets.put(new JSONObject(e)));
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().print(jsonTickets);		
	}
}
