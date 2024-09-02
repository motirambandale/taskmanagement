package com.example.taskmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

}
