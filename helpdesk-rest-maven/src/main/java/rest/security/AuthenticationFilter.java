package rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import java.util.*;
import java.util.stream.*;
import java.io.*;
import lombok.*;

import rest.db.models.UserModel;
import static rest.ApplicationConstants.*;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;
	
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response) throws AuthenticationException {

		return authManager
			.authenticate(
				new UsernamePasswordAuthenticationToken(
					request.getParameter(LOGIN_PARAM_USERNAME), 
					request.getParameter(LOGIN_PARAM_PASSWORD),
					new ArrayList<>()));
	}
	
	@Override
	protected void successfulAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response, 
		FilterChain filterChain, 
		Authentication auth) {
			try {
				//this auth object comes from UserDetailsServiceImpl
				String email = ((User) auth.getPrincipal()).getUsername();	
				List<String> roles = auth.getAuthorities()
					.stream()
					.map(a -> a.getAuthority())
					.collect(Collectors.toList());
				String token = JWT.create()
					.withIssuer(JWT_ISSUER)
					.withClaim(JWT_COOKIE_CLAIM_EMAIL, email)
					.withClaim(JWT_COOKIE_CLAIM_ROLES, roles)
					.withIssuedAt(new Date(System.currentTimeMillis()))
					.withExpiresAt(new Date(System.currentTimeMillis() + JWT_AGE*60000))
					.sign(Algorithm.HMAC256(JWT_KEY));					
				Cookie jwtCookie= new Cookie(JWT_COOKIE_NAME, token);
				jwtCookie.setSecure(true);
				jwtCookie.setPath("/");
				jwtCookie.setMaxAge(JWT_AGE*60);	
				response.addCookie(jwtCookie);		
				System.out.println("LOGIN OK " + email + " " + roles);		
			}
			catch (Exception e) { 
				e.printStackTrace();
			}
	}
	
	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request, 
		HttpServletResponse response, 
		AuthenticationException failed) throws IOException, ServletException {
		System.out.println("LOGIN FAILED");
		response.setStatus(401);		
	}
}
