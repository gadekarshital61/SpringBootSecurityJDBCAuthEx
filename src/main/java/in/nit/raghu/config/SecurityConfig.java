package in.nit.raghu.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		     //create database connection
		    .dataSource(datasource)
		    //fetch un,upwd,uenable using uname input entered in login page
		    .usersByUsernameQuery("select uname,upwd,uenabled from usertab where uname=?")
		     // fetch un,urole using input entered in login page
		    .authoritiesByUsernameQuery("select uname,urole from usertab where uname=?")
		    //provide password encoder object reference
		    .passwordEncoder(passwordEncoder)
		    ;
	
	}
	
	protected void configure(HttpSecurity http)throws Exception{
		//URL access type
		http.authorizeRequests()
		.antMatchers("/home").permitAll()
		.antMatchers("/welcome").authenticated()
		.antMatchers("/admin").hasAuthority("ADMIN")
		.antMatchers("/emp").hasAuthority("EMPLOYEE")
		.antMatchers("/emp").hasAuthority("STUDENT")
		
		//Login details
		.and()
		.formLogin()
      .defaultSuccessUrl("/welcome",true)		
      
		//logout details
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		//exception handling
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied")
		
		;
	
}
}