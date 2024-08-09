package com.cts.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.userservice.exception.UserConflictException;
import com.cts.userservice.exception.UserNotFoundException;
import com.cts.userservice.model.Address;
import com.cts.userservice.model.User;
import com.cts.userservice.repository.UserRepository;
import com.cts.userservice.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User createUser(User user) throws UserConflictException {
//		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (getUserByUsername(user.getUsername()) != null) {
			throw new UserConflictException("User already exists with username " + user.getUsername());
			}
			return userRepository.save(user);
	}

	@Override
	public User updateUser(Long userId, User updatedUser) {
		return userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setCity(updatedUser.getCity());
            user.setState(updatedUser.getState());
            user.setZip(updatedUser.getZip());
            user.setPhone(updatedUser.getPhone());
            user.setCountry(updatedUser.getCountry());
            user.setAddress(updatedUser.getAddress());
            if (!updatedUser.getPassword().isEmpty()) {
                user.setPassword(updatedUser.getPassword());
//                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
		
	}
	
	
	public List<Address> getUserAddresses(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAddresses();
    }
 
    public void addUserAddress(Long userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getAddresses().add(address);
        userRepository.save(user);
    }

}
