package com.app.FirstApp.domain.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.FirstApp.domain.Services.UserService;
//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
	
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;

	private final PasswordEncoder bCryptPasswordEncoder;



	public SecurityConfig(UserService userDetailsService, PasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* =================== change Url /login ====================*/
		CustumAuthenticationFilter custumAuthenticationFilter=new CustumAuthenticationFilter(authenticationManagerBean());
		//custumAuthenticationFilter.setFilterProcessesUrl("/api/login");
		/* ==================== end change Url =======================*/
		http.cors().disable();
		http.csrf().disable();
	
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login","/api/token/refresh/**").permitAll();
		http.authorizeHttpRequests().anyRequest().permitAll();
	
		/*http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("consulter_users"); //authorize only role =role_User to pass requet GET :http://localhost:9098/api/users
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users/save/**").hasAnyAuthority("ajouter_users"); //authorize only role =role_User to pass requet POST :http://localhost:9098/api/users/save
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/users/update/**").hasAnyAuthority("consulter_users"); //authorize only role =role_User to pass requet PUT :http://localhost:9098/api/users/update
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/users/delete/**").hasAnyAuthority("spprimer_users"); //authorize only role =role_User to pass requet PUT :http://localhost:9098/api/users/delete
	
		http.authorizeRequests().anyRequest().authenticated();*/
		/* =============== our filtre =================== */
		http.addFilter(custumAuthenticationFilter);
		http.addFilterBefore(new AuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
		/* =============== end our filtre =============== */
	}
	

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
