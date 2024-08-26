package com.cts.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cts.userservice.dto.LoginRequest;
import com.cts.userservice.security.JwtTokenProvider;
import com.cts.userservice.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginRequest loginRequest) {
	    try {
	        UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

	        Authentication authentication = authenticationManager.authenticate(authenticationToken);

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String token = jwtTokenProvider.generateToken(authentication);
	        return token;
	    } catch (AuthenticationException e) {
	        throw new BadCredentialsException("Invalid username or password");
	    }
	}

}
