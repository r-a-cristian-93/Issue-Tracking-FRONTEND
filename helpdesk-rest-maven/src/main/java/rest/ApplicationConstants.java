package rest;

public class ApplicationConstants {
	public static final String LOGIN_PARAM_USERNAME = "username";
	public static final String LOGIN_PARAM_PASSWORD = "password";
	public static final String LOGIN_PARAM_OK_URL = "okUrl";
	public static final String LOGIN_PARAM_NOK_URL = "nokUrl";
	public static final String JWT_COOKIE_NAME = "JWT";
	public static final String JWT_COOKIE_CLAIM_EMAIL = "email";
	public static final String JWT_COOKIE_CLAIM_ROLES = "roles";
	public static final String JWT_ISSUER = "helpdesk";
	public static final String JWT_KEY = "SecretKeyToGenJWTs";

	public static final int JWT_AGE = 10; // minutes
	
}
