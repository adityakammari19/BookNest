package com.cts.cartservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.cartservice.dto.UserDTO;

@FeignClient("USER-SERVICE")
public interface UserServiceInterface {
	
	 @GetMapping("/api/users/{userId}")
	    UserDTO getUserById(@PathVariable("userId") Long userId);

}
