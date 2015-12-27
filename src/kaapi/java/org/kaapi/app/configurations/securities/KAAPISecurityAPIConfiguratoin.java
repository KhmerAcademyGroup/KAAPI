package org.kaapi.app.configurations.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class KAAPISecurityAPIConfiguratoin extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("RESTAuthenticationEntryPoint")
	RESTAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	@Qualifier(value="APIUserCustomUserDetialSerivce")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication()
			.withUser("KA_API_DEVELOPER")
			.password("!@#$KhmerAcademy")
			.roles("API_DEVELOPER");*/
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest()
			.hasRole("API");
		http.csrf().disable();
		http.httpBasic()
			.authenticationEntryPoint(authenticationEntryPoint);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
