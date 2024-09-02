package com.example.taskmanagement.service;

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
    
}
