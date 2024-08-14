package com.cts.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.userservice.dto.RegisterRequest;
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

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private  PasswordEncoder passwordEncoder; 
	
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
	public User createUser(RegisterRequest newUser) throws UserConflictException {
		User user = new User();
		if (getUserByUsername(newUser.getUsername()) != null) {
			throw new UserConflictException("User already exists with username " + newUser.getUsername());
			}
		user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRoles("ROLE_ADMIN");
			return userRepository.save(user);
	}

	@Override
	public User updateUser(Long userId, User updatedUser) {
		return userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
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
