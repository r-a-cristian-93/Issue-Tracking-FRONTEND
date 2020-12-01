import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/user/listdepartments")
public class ListDepartmentsController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		JSONArray jsonDepartments = new JSONArray();
		try {
			jsonDepartments = Helper.getOptions("departments");
		}
		catch (SQLException e) {}
		response.setContentType("application/json");
		response.getWriter().print(jsonDepartments);		
	}
}
