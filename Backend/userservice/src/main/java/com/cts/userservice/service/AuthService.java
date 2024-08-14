package com.cts.userservice.service;

import com.cts.userservice.dto.LoginRequest;

public interface AuthService {
	
	String login(LoginRequest loginRequest);

}
