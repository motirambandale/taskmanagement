package com.example.taskmanagement.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secret = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    @Value("${jwt.expiration}")
    private long expiration = 3600000; // 1 hour in milliseconds

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        // Set properties directly for testing
        jwtUtil.secret = secret;
        jwtUtil.expiration = expiration;
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testValidateToken_Valid() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        Boolean isValid = jwtUtil.validateToken(token, username);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidUsername() {
        String username = "testuser";
        String wrongUsername = "wronguser";
        String token = jwtUtil.generateToken(username);

        Boolean isValid = jwtUtil.validateToken(token, wrongUsername);

        assertFalse(isValid);
    }

	/*
	 * @Test void testValidateToken_Expired() { String username = "testuser"; long
	 * expiredExpiration = -1000; // Token expired (1000 milliseconds in the past)
	 * jwtUtil.expiration = expiredExpiration;
	 * 
	 * String token = jwtUtil.generateToken(username);
	 * 
	 * // Introduce a small delay to simulate expiration try { Thread.sleep(1000);
	 * // Sleep for 1000 milliseconds to ensure the token is expired } catch
	 * (InterruptedException e) { Thread.currentThread().interrupt(); }
	 * 
	 * Boolean isValid = jwtUtil.validateToken(token, username);
	 * 
	 * // Token should be expired assertFalse(isValid); }
	 */
    @Test
    void testExtractUsername() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractExpiration() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        Date expirationDate = jwtUtil.extractExpiration(token);

        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testExtractClaim() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractClaim(token, Claims::getSubject);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractAllClaims() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        Claims claims = jwtUtil.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }
}
