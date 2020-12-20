package rest.security;

import com.auth0.jwt.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.ArrayList;

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
			
		System.out.println("===================Authorization.doFilterInternal");
		try {	
			Cookie cookies[] = req.getCookies();
			Cookie jwtCookie = null;
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals(JWT_COOKIE_NAME));
				jwtCookie = cookies[i];
			}
			
			//if has no cookie forward request
			if (jwtCookie == null) {
				chain.doFilter(req, res);
				return;
			}
		}
		catch (Exception e) {}
			
			//else process authentication
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		System.out.println("===================Authorization.getAuthentication");
		try {
			Cookie cookies[] = request.getCookies();
			String token = null;
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals(JWT_COOKIE_NAME)) {
					token = cookies[i].getValue();
				}
			}
			
			if (token != null) {
				String user = verify(token);

				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
				return null;
			}
			
		}
		catch (Exception e) {}
		return null;
	}


	private String verify(String token) {
		
		System.out.println("===================Authorization.verify" + token);
		String user = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_KEY))
				.withIssuer(JWT_ISSUER)
				.build();
			DecodedJWT jwt = verifier.verify(token);
			user = jwt.getToken();
		} 
		catch (Exception e){
			e.printStackTrace();			
		}
		return user;
	}
}
