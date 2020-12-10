import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/user/listroles")
public class ListRolesController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		JSONArray jsonRoles = new JSONArray();
		try {
			jsonRoles = Helper.getOptions("roles");
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
			}
		response.setContentType("application/json");
		response.getWriter().print(jsonRoles);		
	}
}
