package rest.security;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import static rest.ApplicationConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
			this.userDetailsService = userDetailsService;
			this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/tickets/add").permitAll()
				.antMatchers(HttpMethod.DELETE, "/tickets/{id}/delete").hasAuthority("owner")
				.antMatchers(HttpMethod.PUT, "/tickets/{id}/close").hasAnyAuthority("admin", "moderator", "owner")
				.antMatchers(HttpMethod.PUT, "/tickets/{id}/update").hasAnyAuthority("moderator", "owner")
				.antMatchers(HttpMethod.GET, "/tickets/mytickets").permitAll()
				.antMatchers(HttpMethod.GET, "/options*").permitAll()
				.antMatchers(HttpMethod.POST, "/usermanagement/register").hasAuthority("owner")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.usernameParameter(LOGIN_PARAM_USERNAME)
				.passwordParameter(LOGIN_PARAM_PASSWORD)
			.and()
				.addFilter(new AuthenticationFilter(authenticationManager()))
				.addFilter(new AuthorizationFilter(authenticationManager()))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);			
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}
}
