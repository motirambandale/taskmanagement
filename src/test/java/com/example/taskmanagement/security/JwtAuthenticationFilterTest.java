package com.example.taskmanagement.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	 * token =
	 * "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTcyNTcyOTQ5NiwiZXhwIjoxNzI1NzMzMDk2fQ.7iiyOpspJL4PGtJVPSKC95Dhm4R9wsAvoZQ6XX-75Do";
	 * String username = "testuser";
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
	 * verify(jwtUtil).extractUsername(token);
	 * verify(jwtUtil).validateToken(token,username);
	 * verify(userDetailsService).loadUserByUsername(username);
	 * verify(chain).doFilter(request, response); }
	 * 
	 */
	/*
	 * @Test void testDoFilterInternal_WithInvalidToken() throws Exception { String
	 * token = "invalid-token";
	 * 
	 * // Mock behavior for invalid token
	 * when(jwtUtil.extractUsername(token)).thenReturn(null); // Simulate extraction
	 * failure when(jwtUtil.validateToken(eq(token), eq(null))).thenReturn(false);
	 * // Token is invalid
	 * 
	 * HttpServletRequest request = mock(HttpServletRequest.class);
	 * HttpServletResponse response = mock(HttpServletResponse.class); FilterChain
	 * chain = mock(FilterChain.class);
	 * 
	 * when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
	 * 
	 * // Invoke the method jwtAuthenticationFilter.doFilterInternal(request,
	 * response, chain);
	 * 
	 * // Verify that extractUsername and validateToken were called with the
	 * expected values verify(jwtUtil).extractUsername(token);
	 * verify(jwtUtil).validateToken(eq(token), eq(null)); // Ensure the token and
	 * null username are passed verify(chain).doFilter(request, response); }
	 * 
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
