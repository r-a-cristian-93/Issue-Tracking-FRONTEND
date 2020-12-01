import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;
import org.json.*;

@WebServlet(value="/owner/register")
public class RegisterController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		JSONArray registerStatus = new JSONArray();
		String email = request.getParameter("email");
		String department = request.getParameter("department");
		String role = request.getParameter("role");

		if(email!=null && department!=null && role!=null){
			String password = Helper.randomPassword(5);
			String sql = "INSERT INTO users (email, department, role, password) VALUES('"+email+"', '"+department+"', '"+role+"', '"+password+"');";
			try {
				Helper.sqlUpdate(sql);
				//send email
				registerStatus.put("Registration successfull. An email has been sent with the password.");
			}
			catch (SQLException e) {
				registerStatus.put(e.getMessage());
			}	
		}
		else {
			registerStatus.put("Invalid request.");
		}		
		response.setContentType("application/json");
		response.getWriter().print(registerStatus);	
	}
}
