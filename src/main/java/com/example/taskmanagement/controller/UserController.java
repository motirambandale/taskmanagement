package com.example.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.AuthRequest;
import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.security.JwtUtil;
import com.example.taskmanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and generate a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "JWT token generated successfully", 
        		     content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<String> login(@RequestBody  AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));        
		 UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
         String jwtToken = jwtUtil.generateToken(userDetails.getUsername());      
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully",
        		                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }
}
