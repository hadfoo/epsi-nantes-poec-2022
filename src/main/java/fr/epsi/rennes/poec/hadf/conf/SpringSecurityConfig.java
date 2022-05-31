package fr.epsi.rennes.poec.hadf.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import fr.epsi.rennes.poec.hadf.domain.UserRole;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource ds;
	
	public SpringSecurityConfig(@Lazy UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/public/**").permitAll()
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/admin/**").hasRole("ADMIN")
		.and()
			.csrf().disable()
			.formLogin()
			.successHandler(new AuthenticationSuccessHandler() {
				// redirige l'utilisateur selon son rôle
				@Override
				public void onAuthenticationSuccess(
						HttpServletRequest request,
						HttpServletResponse response,
						Authentication authentication)
				throws IOException, ServletException {
					
					if (authentication.getAuthorities().contains(UserRole.ROLE_USER)) {
						response.sendRedirect("/user/article-list.html");
					} else if (authentication.getAuthorities().contains(UserRole.ROLE_ADMIN)) {
						response.sendRedirect("/admin/article.html");
					}
				}
			})
		.and()
			.rememberMe()
			.rememberMeCookieName("atelier")
			.tokenRepository(jdbcTokenRepository(ds));
	}
	
    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices(DataSource ds) throws Exception {
    	return new PersistentTokenBasedRememberMeServices("atelier", userDetailsService(), jdbcTokenRepository(ds));
	}
    
    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter(DataSource ds) throws Exception {
    	return new RememberMeAuthenticationFilter(
    			authenticationManagerBean(), rememberMeServices(ds));
    }
    
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(DataSource ds) {
    	JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
    	repo.setDataSource(ds);
    	repo.setCreateTableOnStartup(false);
    	return repo;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
