package com.example.taskmanagement.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private  UserRepository userRepository;
    
	@Autowired
    private  PasswordEncoder passwordEncoder;

    public void saveUser(RegisterRequest registerRequest) {
    	  User newUser = new User();
          newUser.setUsername(registerRequest.getUsername());
          newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword())); 
          newUser.setEmail(registerRequest.getEmail());
          userRepository.save(newUser); 
    }
    
    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    
    public User getLoggedInUser() {
        String username = getLoggedInUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
