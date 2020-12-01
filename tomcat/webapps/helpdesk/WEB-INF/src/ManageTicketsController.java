import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/moderator/filtermanagetickets")
public class ManageTicketsController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String role = ((UserBean) request.getAttribute("user_bean")).getRole();
		String moderatorDepartment = ((UserBean) request.getAttribute("user_bean")).getDepartment();
		String department=request.getParameter("department");
		String status=request.getParameter("status");
			
		JSONArray jsonTickets = new JSONArray();
		try {
			TicketFilterBean filter = new TicketFilterBean().setConcernedDepartment(moderatorDepartment)
															.setOpenedByDepartment(department)
															.setStatus(status);
			ArrayList<TicketBean> tickets = Helper.getTickets(filter);
			tickets.forEach(e->jsonTickets.put(new JSONObject(e)));
		}
		catch (SQLException e) {
			jsonTickets.put(e.getMessage());
		}
		response.setContentType("application/json");
		response.getWriter().print(jsonTickets);		
	}
}
