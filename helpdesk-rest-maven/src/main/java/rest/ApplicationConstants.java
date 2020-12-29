package rest;

import java.util.*;

public class ApplicationConstants {
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://hd_database:3306/helpdesk";
	public static final String DB_USERNAME = "hd_user";
	public static final String DB_PASSWORD = "hd_user";
	public static final String LOGIN_PARAM_USERNAME = "username";
	public static final String LOGIN_PARAM_PASSWORD = "password";
	public static final String JWT_COOKIE_NAME = "JWT";
	public static final String JWT_COOKIE_CLAIM_EMAIL = "email";
	public static final String JWT_COOKIE_CLAIM_ROLES = "roles";
	public static final String JWT_ISSUER = "helpdesk";
	public static final String JWT_KEY = "SecretKeyToGenJWTs";
	public static final int JWT_AGE = 120; // minutes
	public static final String ROLE_USER = "User";
	public static final String ROLE_ADMIN = "Admin";
	public static final String ROLE_MODERATOR = "Moderator";
	public static final String ROLE_OWNER = "Owner";
	public static final List<String> CORS_ORIGINS = Arrays.asList("http://localhost", "https://localhost", "http://localhost:80", "https://localhost:80");
}
