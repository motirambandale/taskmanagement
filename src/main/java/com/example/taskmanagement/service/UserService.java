package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.model.User;

public interface UserService {
	 public void saveUser(RegisterRequest registerRequest);
	 public String getLoggedInUsername();
	 public User getLoggedInUser();
}
