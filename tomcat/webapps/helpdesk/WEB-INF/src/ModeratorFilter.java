import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;
import java.util.*;

@WebFilter(urlPatterns = {"/moderator/*"})
public class ModeratorFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		DecodedJWT jwt = Helper.verifyJwt(request);
			
		if(jwt!=null) {
			String role = jwt.getClaim("user_role").asString();
			if (role.equals("Moderator") || role.equals("Owner")) {
				servletRequest.setAttribute("user_bean", new UserBean().build(jwt));
				chain.doFilter(servletRequest, servletResponse);
			}
			else {
				response.sendRedirect(request.getContextPath()+"/user/not-allowed.html");
			}
		} 
		else {
			response.sendRedirect(request.getContextPath()+"/login.html");
		}
	}
}
