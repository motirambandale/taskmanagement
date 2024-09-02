package com.example.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Registration request payload")
public class RegisterRequest {

	@Schema(description = "Username for registration", example = "user123")
	private String username;

	@Schema(description = "Password for registration", example = "password123")
	private String password;

	@Schema(description = "Email address of the user", example = "user@example.com")
	private String email;

}