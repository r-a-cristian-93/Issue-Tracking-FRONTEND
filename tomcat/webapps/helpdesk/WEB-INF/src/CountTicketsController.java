import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.*;

@WebServlet(value="/user/counttickets")
public class CountTicketsController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int userId=((UserBean) request.getAttribute("user_bean")).getId();
		
		JSONObject jsonCount = new JSONObject();
		try {
			jsonCount = Helper.getTicketsCount(userId);
		}
		catch (SQLException e) {
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().print(jsonCount);		
	}
}
