package com.example.taskmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.taskmanagement.dto.AuthRequest;
import com.example.taskmanagement.dto.RegisterRequest;
import com.example.taskmanagement.security.JwtUtil;
import com.example.taskmanagement.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

class UserControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("user");
        authRequest.setPassword("password");

        UserDetails userDetails = org.mockito.Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user");

        // Mocking the authentication and token generation process
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Assuming this method does not return anything useful in this case
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.generateToken("user")).thenReturn("mocked-jwt-token");

        // Act
        ResponseEntity<String> response = userController.login(authRequest);

        // Assert
        assertEquals(ResponseEntity.ok("mocked-jwt-token"), response);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername("user");
        verify(jwtUtil).generateToken("user");
    }


    @Test
    void testRegister_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("newuser@example.com");

        doNothing().when(userService).saveUser(any(RegisterRequest.class));

        // Act
        ResponseEntity<String> response = userController.register(registerRequest);

        // Assert
        assertEquals(ResponseEntity.ok("User registered successfully"), response);
        verify(userService).saveUser(registerRequest);
    }
}
