package rest.security;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Date;

import rest.db.models.UserModel;
import static rest.ApplicationConstants.*;
/*
import static rest.ApplicationConstants.LOGIN_PARAM_USERNAME;
import static rest.ApplicationConstants.LOGIN_PARAM_PASSWORD;
import static rest.ApplicationConstants.LOGIN_PARAM_NOK_URL;
import static rest.ApplicationConstants.LOGIN_PARAM_OK_URL;
*/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;

	public AuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response) throws AuthenticationException {
		
		try {
			UserModel credentials = UserModel.getInstance();			
			credentials.setEmail(request.getParameter(LOGIN_PARAM_USERNAME));
			credentials.setPassword(request.getParameter(LOGIN_PARAM_PASSWORD));
						
			return authManager
				.authenticate(
					new UsernamePasswordAuthenticationToken(
						credentials.getEmail(), 
						credentials.getPassword(),
						new ArrayList<>()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response, 
		FilterChain filterChain, 
		Authentication auth) {
			try {
				String email = ((User) auth.getPrincipal()).getUsername();			
				String redirUrl = request.getParameter(LOGIN_PARAM_OK_URL);
				String token = JWT.create()
					.withIssuer(JWT_ISSUER)
					.withClaim(JWT_COOKIE_CLAIM_EMAIL, email)
					.withIssuedAt(new Date(System.currentTimeMillis()))
					.withExpiresAt(new Date(System.currentTimeMillis() + JWT_AGE*60000))
					.sign(Algorithm.HMAC256(JWT_KEY));					
				Cookie jwtCookie= new Cookie(JWT_COOKIE_NAME, token);
				jwtCookie.setSecure(true);
				jwtCookie.setPath("/");
				jwtCookie.setMaxAge(JWT_AGE*60);
								
				response.addCookie(jwtCookie);
				response.sendRedirect(request.getContextPath() + redirUrl);
			}
			catch (Exception e) {}
	}
	
	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response, 
		AuthenticationException failed) {
			try {			
				String redirUrl = request.getParameter(LOGIN_PARAM_NOK_URL);
				response.sendRedirect(request.getContextPath() + redirUrl);
			}
			catch (Exception e) {}
	}	
}
	
	
		
		
