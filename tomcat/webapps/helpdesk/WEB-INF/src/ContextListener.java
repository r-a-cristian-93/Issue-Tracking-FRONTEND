import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
	
@WebListener
public class ContextListener implements ServletContextListener {
	private static String sqlAddress;
	private static String sqlUser;
	private static String sqlPassword;
	private static String jwtIssuer;
	private static String jwtKey;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		this.sqlAddress = context.getInitParameter("sqlAddress");
		this.sqlUser = context.getInitParameter("sqlUser");
		this.sqlPassword = context.getInitParameter("sqlPassword");
		this.jwtIssuer = context.getInitParameter("jwtIssuer");
		this.jwtKey = context.getInitParameter("jwtKey");
	}
	
	public static String getSqlAddress() {
		return sqlAddress;
	}
	public static String getSqlUser() {
		return sqlUser;
	}
	public static String getSqlPassword() {
		return sqlPassword;
	}	
	public static String getJwtIssuer() {
		return jwtIssuer;
	}
	public static String getJwtKey() {
		return jwtKey;
	}
}
