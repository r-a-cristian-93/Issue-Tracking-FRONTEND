package rest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.*;

import static rest.ApplicationConstants.*;


public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest req,
		HttpServletResponse res,
		FilterChain chain) throws IOException, ServletException {
			
		String jwtToken = getToken(req);			
		if (jwtToken != null) {		
			UsernamePasswordAuthenticationToken authentication = getAuthentication(jwtToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(req, res);
	}
	
	private String getToken(HttpServletRequest request){
		Cookie cookies[] = request.getCookies();
		for(Cookie c:cookies) {
			if(c.getName().equals(JWT_COOKIE_NAME)) {
				return c.getValue();
			}
		}	
		return null;
	}		

	private UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
		DecodedJWT jwt = JWT.require(Algorithm.HMAC256(JWT_KEY))
			.withIssuer(JWT_ISSUER)
			.build()
			.verify(jwtToken);
		String user = jwt
			.getClaim(JWT_COOKIE_CLAIM_EMAIL)
			.asString();
		List<SimpleGrantedAuthority> roles = jwt
			.getClaim(JWT_COOKIE_CLAIM_ROLES)
			.asList(SimpleGrantedAuthority.class);

		System.out.println("JWT decoded: " + user + " roles:  " + roles);
		return new UsernamePasswordAuthenticationToken(user, null, roles);
	}
}
