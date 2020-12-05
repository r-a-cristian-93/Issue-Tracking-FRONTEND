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
			try {
				int length = ContextListener.getIntParam("pwdLength");
				String password = Helper.randomPassword(length);
				String passwordHash = Password.generateHash(password);
				String sql = "INSERT INTO users (email, department, role, password) VALUES('"+email+"', '"+department+"', '"+role+"', '"+passwordHash+"');";
				Helper.sqlUpdate(sql);
				//send email
				registerStatus.put("Registration successfull. An email has been sent with the password." + password);
				
			}
			catch (Exception e) {
				response.sendError(500, "Server Error");
				e.printStackTrace();
			}	
		}
		else {
			registerStatus.put("Please complete all fields.");
		}		
		response.setContentType("application/json");
		response.getWriter().print(registerStatus);	
	}
}
