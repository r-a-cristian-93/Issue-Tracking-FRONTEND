import com.auth0.jwt.interfaces.*;

public class UserBean {
	private int id;
	private String email;
	private String department;
	private String role;
	
	public UserBean() {}
	
	
	public UserBean setId(int id) {
		this.id=id;
		return this;
	}
	public UserBean setEmail(String email) {
		this.email=email;
		return this;
	}
	public UserBean setDepartment(String department) {
		this.department=department;
		return this;
	}
	public UserBean setRole(String role) {
		this.role=role;
		return this;
	}
	
	public int getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getDepartment() {
		return department;
	}
	public String getRole() {
		return role;
	}	
	
	public UserBean build(DecodedJWT jwt) {						
		return new UserBean()
						.setId(jwt.getClaim("user_id").asInt())
						.setEmail(jwt.getClaim("user_email").asString())
						.setRole(jwt.getClaim("user_role").asString())
						.setDepartment(jwt.getClaim("user_department").asString());
	}
}
