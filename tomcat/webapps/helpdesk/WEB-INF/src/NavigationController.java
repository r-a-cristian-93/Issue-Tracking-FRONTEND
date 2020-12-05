import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/user/navigation")
public class NavigationController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		UserBean userBean = (UserBean) request.getAttribute("user_bean");
		String role = userBean.getRole();
		
		JSONArray navigation = new JSONArray();
		navigation.put(new JSONObject().put("name", userBean.getEmail())
										.put("role", role)
										.put("url", "../user/dashboard.html"));
		navigation.put(new JSONObject(new NavigationItemBean().setState("Dashboard", "../user/dashboard.html")));
		navigation.put(new JSONObject(new NavigationItemBean().setState("New Ticket", "../user/new-ticket.html")));
		navigation.put(new JSONObject(new NavigationItemBean().setState("My Tickets", "../user/my-tickets.html")));
		
		if(role.equals("Admin")) {
			navigation.put(new JSONObject(new NavigationItemBean().setState("Review Tickets", "../admin/review-tickets.html")));
		}
		if(role.equals("Moderator")) {
			navigation.put(new JSONObject(new NavigationItemBean().setState("Manage Tickets", "../moderator/manage-tickets.html")));
		}
		if(role.equals("Owner")) {
			navigation.put(new JSONObject(new NavigationItemBean().setState("Manage Tickets", "../owner/manage-tickets.html")));
			navigation.put(new JSONObject(new NavigationItemBean().setState("Register", "../owner/register.html")));
		}
		response.setContentType("application/json");
		response.getWriter().print(navigation);
	}
}
