package com.example.taskmanagement.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Map;

class OpenAPIConfigTest {

    @InjectMocks
    private OpenAPIConfig openAPIConfig; // Class under test
    
    @Mock
    private OpenAPI openAPIMock; // Mock the OpenAPI
    
    @Mock
    private Paths pathsMock; // Mock the Paths object

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMyOpenAPI() {
        // Act
        OpenAPI openAPI = openAPIConfig.myOpenAPI();

        // Assert
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Task API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("This API exposes endpoints to manage tasks.", openAPI.getInfo().getDescription());

        // Verify server settings
        assertNotNull(openAPI.getServers());
        assertEquals(1, openAPI.getServers().size());
        Server server = openAPI.getServers().get(0);
        assertEquals("http://localhost:7777", server.getUrl());
        assertEquals("Server URL", server.getDescription());

        // Verify security requirements
        assertNotNull(openAPI.getComponents());
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertNotNull(securityScheme);
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());
    }

    @Test
    void testFilterPaths_removesApiPaths() {
        // Arrange: Setup mock paths with a mix of '/api' and non '/api' paths
        Map<String, io.swagger.v3.oas.models.PathItem> mockPaths = Map.of(
                "/api/tasks", mock(io.swagger.v3.oas.models.PathItem.class),
                "/tasks", mock(io.swagger.v3.oas.models.PathItem.class)
        );
        when(openAPIMock.getPaths()).thenReturn(pathsMock);
        when(pathsMock.entrySet()).thenReturn(mockPaths.entrySet());

        // Act: Call the method that filters paths
        openAPIConfig.filterPaths(openAPIMock);

        // Assert: Verify that only the non '/api' paths remain
        verify(pathsMock).clear();
        verify(pathsMock).addPathItem(eq("/tasks"), any());
        verify(pathsMock, never()).addPathItem(eq("/api/tasks"), any());
    }
}
