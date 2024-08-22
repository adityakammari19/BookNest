package com.cts.userservice.service;

import java.util.List;
import java.util.Optional;

import com.cts.userservice.dto.RegisterRequest;
import com.cts.userservice.dto.UpdateUserRequest;
import com.cts.userservice.exception.UserConflictException;
import com.cts.userservice.model.Address;
import com.cts.userservice.model.User;

public interface UserService {

	public List<User> getAllUsers();
	public Optional<User> getUserById(Long userId);
	 public User getUserByUsername(String username);
	 public User getUserByEmail(String email);
	 public User createUser(RegisterRequest user) throws UserConflictException ;
	 public User createAdminUser(RegisterRequest user) throws UserConflictException ;
	 public User updateUser(Long userId, UpdateUserRequest updatedUser);
	 public void deleteUser(Long userId);
	 public List<Address> getUserAddresses(Long userId);
	 public void addUserAddress(Long userId, Address address);
	 
	 
	
}
