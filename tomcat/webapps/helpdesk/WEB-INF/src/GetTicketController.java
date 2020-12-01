import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/admin/getticket")
public class GetTicketController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.valueOf(request.getParameter("id"));
			
		JSONArray jsonTickets = new JSONArray();
		try {
			ArrayList<TicketBean> tickets = Helper.getTickets(new TicketFilterBean().setTicketId(id));
			tickets.forEach(e->jsonTickets.put(new JSONObject(e)));
		}
		catch (SQLException e) {
			jsonTickets.put(e.getMessage());
		}
		response.setContentType("application/json");
		response.getWriter().print(jsonTickets);		
	}
}
