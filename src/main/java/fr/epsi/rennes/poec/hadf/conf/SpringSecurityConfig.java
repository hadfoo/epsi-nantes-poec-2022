package fr.epsi.rennes.poec.hadf.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
	
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
			.defaultSuccessUrl("/user/article-list.html")
		.and()
			.rememberMe()
			.rememberMeCookieName("atelier");
	}

    @Bean("rememberMeServices")
    public TokenBasedRememberMeServices rememberMeServices() throws Exception {
    	return new TokenBasedRememberMeServices("atelier", userDetailsService());
	}
    
    @Bean("rememberMeFilter")
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
    	return new RememberMeAuthenticationFilter(
    			authenticationManagerBean(), rememberMeServices());
    }
    
    @Bean("rememberMeAuthenticationProvider")
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
    	return new RememberMeAuthenticationProvider("atelier");
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
