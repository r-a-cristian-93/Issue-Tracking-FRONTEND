package rest.security;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

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

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;

	public AuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response) throws AuthenticationException {
			
		System.out.println("======================Authentication.attemptAuthentication " + request.getRequestURL());
		
		try {
			UserModel credentials = UserModel.getInstance();
			System.out.println("======================" + "Attempting with " + request.getParameter("username") + " / " + request.getParameter("password"));
			credentials.setEmail(request.getParameter("username"));
			credentials.setPassword(request.getParameter("password"));
			System.out.println("======================" + "Attempting with " + credentials.getEmail() + " / " + credentials.getPassword());
			
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
			
			System.out.println("======================Authentication.succesfull" + request.getRequestURL());
			try {
				String email = ((User) auth.getPrincipal()).getUsername();			
				String key = "secret";
				String issuer = "helpdesk";
				String redirUrl = request.getParameter("okUrl");
				System.out.println("======================Authentication.succesfull URL" + request.getParameter("okUrl"));

				String token = JWT.create()
					.withIssuer(issuer)
					.withClaim("userEmail", email)
					.withIssuedAt(new Date(System.currentTimeMillis()))
					.withExpiresAt(new Date(System.currentTimeMillis() + 3600000))  //+one hour
					.sign(Algorithm.HMAC256(key));
					
				Cookie jwtCookie= new Cookie("JWT", token);
				jwtCookie.setSecure(true);
				jwtCookie.setPath("/");
				jwtCookie.setMaxAge(60*60*1);
				
				response.addCookie(jwtCookie);
				response.sendRedirect(request.getContextPath() + redirUrl);
			}
			catch (Exception e) {}
	}
/*	
	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response, 
		AuthenticationException failed) {			
			String redirUrl = request.getParameter("nokUrl");
			response.sendRedirect(request.getContextPath() + redirUrl);
	}
	*/		
}
	
	
		
		
