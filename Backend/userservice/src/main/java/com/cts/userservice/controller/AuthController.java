package com.cts.userservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.userservice.dto.LoginRequest;
import com.cts.userservice.exception.UserNotFoundException;
import com.cts.userservice.model.User;
import com.cts.userservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
    private UserService userService;
 
//    @Autowired
//    private PasswordEncoder passwordEncoder;
 
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }
 
    @PostMapping("/login")
    public User loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = Optional.of(userService.getUserByUsername(loginRequest.getUsername()));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
//            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//                return user;
//            }
            if (loginRequest.getPassword().equals(user.getPassword())) {
            	return user;
			}
        }
        throw new UserNotFoundException("Invalid username or password");
    }
	
	
	
	
	
	
	
//	@Autowired
//	private  AuthService authService;
//	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
//    }
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
//        return ResponseEntity.ok(authService.registerUser(registerRequest));
//    }

	
	

}
