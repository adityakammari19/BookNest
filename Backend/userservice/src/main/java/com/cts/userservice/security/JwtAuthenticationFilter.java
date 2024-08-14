package com.cts.userservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token=getTokenFromRequest(request);
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			String username=jwtTokenProvider.getUsername(token);
			
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authToken
				=new UsernamePasswordAuthenticationToken
					(userDetails, null,userDetails.getAuthorities());
			
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}
	
private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearerToken=request.getHeader("Authorization");
		System.out.println("Bearer token ========"+bearerToken);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			
			String token=bearerToken.substring(7);
			System.out.println("Jwt Token ==========="+token);
			return token;
		}
		return null;
		
	}

}
