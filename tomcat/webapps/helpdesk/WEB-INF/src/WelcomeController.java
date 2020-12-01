import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//@WebServlet(value="")
public class WelcomeController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		boolean authenticated = true;
		if(authenticated) {
			String url = request.getRequestURL()+"user/index.html";
			System.out.println(url);
			response.sendRedirect(url);			
		}
		else {
			response.sendRedirect(request.getRequestURL()+"/login.html");
		}
	}
}
