import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import java.util.*;

@WebServlet(value="/login")
public class LoginController extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passwordHash = "";
		
		try {
			ResultSet resultSet = Helper.sqlQuery("SELECT * FROM users WHERE email='"+email+"';");
			//if email was found in database
			if(resultSet.next()){
				String storedPasswordHash = resultSet.getString("password");
				passwordHash = Password.generatePasswordHash(password);
				if(passwordHash.equals(storedPasswordHash)){
					String user_role = resultSet.getString("role");
					String user_department = resultSet.getString("department");
					int user_id = resultSet.getInt("ID");
					try {
						String key = ContextListener.getJwtKey();
						String issuer = ContextListener.getJwtIssuer();
						Algorithm algorithm = Algorithm.HMAC256(key);
						String token = JWT.create()
							.withIssuer(issuer)
							.withClaim("user_id", user_id)
							.withClaim("user_role", user_role)
							.withClaim("user_email", email)
							.withClaim("user_department", user_department)
							.withIssuedAt(new java.util.Date(System.currentTimeMillis()))
							.withExpiresAt(new java.util.Date(System.currentTimeMillis() + 3600000))  //+one hour
							.sign(algorithm);
							
						Cookie jwtCookie= new Cookie("JWT", token);
						jwtCookie.setSecure(true);
						jwtCookie.setPath("/");
						jwtCookie.setMaxAge(60*60*1);
						
						response.addCookie(jwtCookie);
						response.sendRedirect(request.getContextPath()+"/user/dashboard.html");
					} catch (JWTCreationException e){
						response.setContentType("text/html");
						response.getWriter().print(e.getMessage());}			
				}
				else {
					response.setContentType("text/html");
					response.getWriter().print("Invalid Password.");
				}
			}
			else {
				response.setContentType("text/html");
				response.getWriter().print("Invalid Email address.");
			}
		}
		catch(Exception e) {
			response.setContentType("text/html");
			response.getWriter().print(e.getMessage());
		}
	}
}





