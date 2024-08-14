package com.cts.userservice.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.userservice.model.MyUserDetails;
import com.cts.userservice.model.User;
import com.cts.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

private final UserRepository userRepository;
	
    public CustomUserDetailsService(UserRepository userRepository) { 
        this.userRepository = userRepository; 
    } 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username); 
        if (user == null) { 
            throw new UsernameNotFoundException("User not found with username: " + username); 
        } 
         return new MyUserDetails(user);
	}
}
