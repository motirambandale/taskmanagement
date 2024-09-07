package com.example.taskmanagement.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationFilter).build();
    }
	/*
	 * @Test void testDoFilterInternal_WithValidToken() throws Exception { String
	 * token = "valid-token"; String username = "testuser";
	 * 
	 * when(jwtUtil.extractUsername(token)).thenReturn(username);
	 * when(jwtUtil.validateToken(token, username)).thenReturn(true);
	 * when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails)
	 * ;
	 * 
	 * HttpServletRequest request = mock(HttpServletRequest.class);
	 * HttpServletResponse response = mock(HttpServletResponse.class); FilterChain
	 * chain = mock(FilterChain.class);
	 * 
	 * when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
	 * 
	 * jwtAuthenticationFilter.doFilterInternal(request, response, chain);
	 * 
	 * verify(jwtUtil).extractUsername(token); verify(jwtUtil).validateToken(token,
	 * username); verify(userDetailsService).loadUserByUsername(username);
	 * verify(chain).doFilter(request, response); }
	 */
	/*
	 * @Test void testDoFilterInternal_WithInvalidToken() throws Exception { String
	 * token = "invalid-token"; String username = "testuser";
	 * 
	 * // Mock behavior for invalid token
	 * when(jwtUtil.extractUsername(token)).thenReturn(username);
	 * when(jwtUtil.validateToken(eq(token), any(String.class))).thenReturn(false);
	 * // Ensuring token validation returns false
	 * when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails)
	 * ;
	 * 
	 * HttpServletRequest request = mock(HttpServletRequest.class);
	 * HttpServletResponse response = mock(HttpServletResponse.class); FilterChain
	 * chain = mock(FilterChain.class);
	 * 
	 * when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
	 * 
	 * jwtAuthenticationFilter.doFilterInternal(request, response, chain);
	 * 
	 * verify(jwtUtil).extractUsername(token);
	 * verify(jwtUtil).validateToken(eq(token), eq(username)); // Ensure the
	 * arguments are correctly matched
	 * verify(userDetailsService).loadUserByUsername(username);
	 * verify(chain).doFilter(request, response); }
	 */

    @Test
    void testDoFilterInternal_WithNoToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
    }
}
