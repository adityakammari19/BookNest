package com.cts.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.userservice.dto.AuthResponse;
import com.cts.userservice.dto.LoginRequest;
import com.cts.userservice.dto.RegisterRequest;
import com.cts.userservice.exception.UserConflictException;
import com.cts.userservice.model.User;
import com.cts.userservice.service.AuthService;
import com.cts.userservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private AuthService authService;
 
    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest registerRequest) throws UserConflictException {
        return userService.createUser(registerRequest);
    }
 
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
//        Optional<User> userOptional = Optional.of(userService.getUserByUsername(loginRequest.getUsername()));
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
////            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
////                return user;
////            }
//            if (loginRequest.getPassword().equals(user.getPassword())) {
//            	return user;
//			}
//        }
//        throw new UserNotFoundException("Invalid username or password");
        
        System.out.println("Inside Login===============");
		String token=authService.login(loginRequest);
		System.out.println( "Username " + loginRequest.getUsername());
		AuthResponse authResponse=new AuthResponse();
		authResponse.setAccessToken(token);
		return ResponseEntity.ok(authResponse);
    }
	
	
//    @PostMapping("/token")
//    public String getToken(@RequestBody LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        if (authenticate.isAuthenticated()) {
//            return authService.generateToken(loginRequest.getUsername());
//        } else {
//            throw new RuntimeException("invalid access");
//        }
//    }
//
//    @GetMapping("/validate")
//    public String validateToken(@RequestParam("token") String token) {
//    	authService.validateToken(token);
//        return "Token is valid";
//    }
	
	
	
	
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
