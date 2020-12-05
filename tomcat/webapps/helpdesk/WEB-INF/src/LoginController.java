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
			String sql ="SELECT * FROM users WHERE email=?";
			PreparedStatement prepStatement= Helper.getPreparedStatement(sql);
			prepStatement.setString(1, email);
			ResultSet resultSet = prepStatement.executeQuery();
			//if email was found in database
			if(resultSet.next()){
				String storedPasswordHash = resultSet.getString("password");
				passwordHash = Password.generateHash(password);
				if(passwordHash.equals(storedPasswordHash)){
					String user_role = resultSet.getString("role");
					String user_department = resultSet.getString("department");
					int user_id = resultSet.getInt("ID");
					{
						String key = ContextListener.getStringParam("jwtKey");
						String issuer = ContextListener.getStringParam("jwtIssuer");
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
					}			
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
			response.sendError(500, "Server Error");
			e.printStackTrace();
		}
	}
}





