package org.kaapi.app.configurations.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class KAAPISecurityWebConfiguration extends WebSecurityConfigurerAdapter{
	
	/*@Autowired
	@Qualifier(value="customSuccessHandler")
	private CustomSuccessHandler successHandler;*/
	
	@Autowired
	@Qualifier(value="ajaxAuthenticationSuccessHandler")
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
	
	@Autowired
	@Qualifier(value="ajaxAuthenticationFailureHandler")
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
	
	@Autowired
	@Qualifier(value="APIUserCustomUserDetialSerivce")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*auth.inMemoryAuthentication()
			.withUser("admin")
			.password("admin")
			.roles("ADMIN");
		
		auth.inMemoryAuthentication()
			.withUser("leader")
			.password("leader")
			.roles("KA_CLIENT");
	
		auth.inMemoryAuthentication()
			.withUser("member")
			.password("member")
			.roles("KA_CLIENT_MEMBER");*/
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/websockets/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/wsapi/**").hasAnyRole("USER","ADMIN")
			.antMatchers("/user/**").hasAnyRole("USER" , "ADMIN")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/participants").permitAll();
		http
			.formLogin()
			.permitAll()
			.loginPage("/login")
			.successHandler(ajaxAuthenticationSuccessHandler)
//			.successHandler(successHandler)
			.usernameParameter("ka_username")
			.passwordParameter("ka_password")
//			.failureUrl("/login?error");
			.failureHandler(ajaxAuthenticationFailureHandler);
		http
			.sessionManagement()
			.sessionAuthenticationErrorUrl("/login")
//			.sessionFixation()
//			.changeSessionId()
			.maximumSessions(1)
			.maxSessionsPreventsLogin(true)
			.expiredUrl("/login")
			.sessionRegistry(sessionRegistryImpl());
		http
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			.invalidateHttpSession(true)
			.deleteCookies("JESSIONID")
			.permitAll();
		http.csrf().disable();
		http.exceptionHandling().accessDeniedPage("/accessDenied");
	}

	@Bean
	protected SessionRegistry sessionRegistryImpl(){
		return new SessionRegistryImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
