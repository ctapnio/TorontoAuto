/*
 * Christian Tapnio
 * 991359879
 * Assignment 3
 * */
package ca.sheridancollege.tapnioc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ca.sheridancollege.tapnioc.services.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {// filtering requests
		http.csrf().disable();
		http.headers().frameOptions().disable();// needed for running H2
		
		http.authorizeRequests()
		.antMatchers("/user/**").hasRole("USER")// if url and user matches

		.and().formLogin().loginPage("/login").permitAll()

		.and().logout().invalidateHttpSession(true)// stop current session
		.clearAuthentication(true)// in memory authentication
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		.permitAll()

		.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")// if url and user matches
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**").permitAll()
				.antMatchers("/h2-console/**").permitAll()// H2
				.anyRequest().authenticated()

				.and().formLogin().loginPage("/login").permitAll()

				.and().logout().invalidateHttpSession(true)// stop current session
				.clearAuthentication(true)// in memory authentication
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll()

				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}


}
