package com.example.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Login request payload")
public class AuthRequest {

    @Schema(description = "Username for authentication", example = "user123")
    private String username;

    @Schema(description = "Password for authentication", example = "password123")
    private String password;

}
