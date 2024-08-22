package com.cts.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.userservice.security.JwtAuthenticationEntryPoint;
import com.cts.userservice.security.JwtAuthenticationFilter;
import com.cts.userservice.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        
	    	httpSecurity.csrf(csrfConfig->csrfConfig.disable())
	        		.authorizeHttpRequests(auth->auth
	        				.requestMatchers(HttpMethod.OPTIONS , "/**").permitAll()
	        				.requestMatchers(HttpMethod.POST,"/api/auth/register","/api/auth/login","/api/auth/admin/register").permitAll()
//	        				.requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
	        				.anyRequest().authenticated())
	                
	        		.exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
	        		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        		;
	        		httpSecurity.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	        		
	        	
	        		return httpSecurity.build();
	    }

	    
}
