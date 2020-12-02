import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;

import org.json.*;

public class Helper {	
	public static Connection getConnection() throws SQLException {
		String url = ContextListener.getStringParam("sqlAddress");
		String user =  ContextListener.getStringParam("sqlUser");
		String password = ContextListener.getStringParam("sqlPassword");
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}
	
	public static Statement getStatement() throws SQLException{
		return Helper.getConnection().createStatement();
	}
	
	public static ResultSet sqlQuery(String sql) throws SQLException{
		return Helper.getStatement().executeQuery(sql);
	}
	
	public static int sqlUpdate(String sql) throws SQLException{
		return Helper.getStatement().executeUpdate(sql);
	}
	
	public static String randomPassword(int length) {
		StringBuilder password = new StringBuilder();
		Random generator = new Random();
		for (int i=0; i<length; i++) {
			char c =  (char) (generator.nextInt(93) + 33);
			password.append(c);
		}
		return password.toString();
	}
	
	public static String getBody(HttpServletRequest request) {
		StringBuilder body = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while((line = reader.readLine()) != null) {
				body.append(line);
			}
			return new String(body);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static ArrayList<TicketBean> getTickets(TicketFilterBean ticketFilter) throws SQLException {
		ArrayList<TicketBean> tickets = new ArrayList<>();
		
		String sql = "SELECT t.ID, t.status, t.summary, t.issue, uo.email opened_by, uo.department opened_by_department, ua.email assigned_to, uc.email closed_by "+ 
							"FROM tickets t "+
							"LEFT JOIN users uo ON t.opened_by=uo.ID "+
							"LEFT JOIN users ua ON t.assigned_to=ua.ID "+
							"LEFT JOIN users uc ON t.closed_by=uc.ID "+
							ticketFilter + ";";
		
		ResultSet resultSet = Helper.sqlQuery(sql);
		while(resultSet.next()){
			int id = resultSet.getInt("ID");
			String status = resultSet.getString("status");
			String summary = resultSet.getString("summary");
			String issue = resultSet.getString("issue");
			String openedBy = resultSet.getString("opened_by");
			String openedByDepartment = resultSet.getString("opened_by_department");
			String assignedTo = resultSet.getString("assigned_to");
			String closedBy = resultSet.getString("closed_by");
			
			tickets.add(new TicketBean().setId(id)
										.setStatus(status)
										.setSummary(summary)
										.setIssue(issue)
										.setOpenedBy(openedBy)
										.setOpenedByDepartment(openedByDepartment)
										.setAssignedTo(assignedTo)
										.setClosedBy(closedBy));
		}
		return tickets;
	}
	
	public static JSONArray getAdmins(String moderatorDepartment) throws SQLException {
		JSONArray admins = new JSONArray();

		String sql = "SELECT ID, email FROM users WHERE role='Admin' AND department='"+moderatorDepartment+"';";
									
		ResultSet resultSet = Helper.sqlQuery(sql);
		while(resultSet.next()){
			int id = resultSet.getInt("ID");
			String email = resultSet.getString("email");
			System.out.println(id+" " + email);
			admins.put(new JSONObject().put("id", id).put("email", email));
		}
		return admins;
	}
	
	public static JSONArray getAdmins() throws SQLException {
		JSONArray admins = new JSONArray();

		String sql = "SELECT ID, email FROM users WHERE role='Admin';";
									
		ResultSet resultSet = Helper.sqlQuery(sql);
		while(resultSet.next()){
			int id = resultSet.getInt("ID");
			String email = resultSet.getString("email");
			System.out.println(id+" " + email);
			admins.put(new JSONObject().put("id", id).put("email", email));
		}
		return admins;
	}	
	
	public static JSONArray getOptions(String table) throws SQLException {
		JSONArray options = new JSONArray();
		String sql = "SELECT * FROM " + table + ";";
		ResultSet resultSet = Helper.sqlQuery(sql);
		while(resultSet.next()){
			String option = resultSet.getString("value");
			options.put(option);
		}
		return options;
	}	
	
	public static JSONObject getTicketsCount(int userId) throws SQLException {
		JSONObject count = new JSONObject();
		String sql = "SELECT status, COUNT(*) no_of_tickets from tickets WHERE opened_by="+userId+" GROUP BY status;";
		ResultSet resultSet = Helper.sqlQuery(sql);
		while(resultSet.next()){
			String status = resultSet.getString("status");
			int noOfTickets = Integer.valueOf(resultSet.getString("no_of_tickets"));
			count.put(status, noOfTickets);
		}
		return count;
	}	

	public static String getToken(HttpServletRequest request) {
		String token = null;		
		try {
			javax.servlet.http.Cookie[] reqCookies = request.getCookies();
			for (int i=0; i<reqCookies.length; i++) {
				if(reqCookies[i].getName().equals("JWT")) {
					token = reqCookies[i].getValue();
				}
			}				
		}
		catch(NullPointerException e) {}
		return token;
	}
	
	public static DecodedJWT verifyJwt(HttpServletRequest request) {
		String token = Helper.getToken(request);
		DecodedJWT jwt = null;
	
		try {
			String key = ContextListener.getStringParam("jwtKey");
			String issuer = ContextListener.getStringParam("jwtIssuer");
			Algorithm algorithm = Algorithm.HMAC256(key);
			JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer(issuer)
				.build();
			 jwt = verifier.verify(token);
		} 
		catch (Exception e){}
		
		return jwt;
	}
}	
