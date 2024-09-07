package com.example.taskmanagement.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.taskmanagement.model.User;
import com.example.taskmanagement.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testuser";
        String password = "password";

        // Create a mock user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // Mock the behavior of the repository
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Call the method under test
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Verify the results
        assertNotNull(userDetails, "UserDetails should not be null");
        assertTrue(userDetails instanceof org.springframework.security.core.userdetails.User, 
                   "UserDetails should be an instance of org.springframework.security.core.userdetails.User");
        assertEquals(username, userDetails.getUsername(), "Username should match");
        assertEquals(password, userDetails.getPassword(), "Password should match");

        // Verify interactions with the mock
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "unknownuser";

        // Mock the behavior of the repository to return an empty Optional
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Call the method under test and assert that it throws UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });

        // Verify interactions with the mock
        verify(userRepository).findByUsername(username);
    }
}
