import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/moderator/listadmins")
public class ListAdminsController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String role = ((UserBean) request.getAttribute("user_bean")).getRole();
		JSONArray jsonAdmins = new JSONArray();
		try {
			if(role.equals("Owner")) {
				jsonAdmins = Helper.getAdmins();
			}
			else {
				String moderatorDepartment = ((UserBean) request.getAttribute("user_bean")).getDepartment();				
				jsonAdmins = Helper.getAdmins(moderatorDepartment);
			}
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().print(jsonAdmins);		
	}
}
