package com.example.taskmanagement.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:7777");
        server.setDescription("Server URL");

        Info info = new Info()
                .title("Task API")
                .version("1.0")
                .description("This API exposes endpoints to manage tasks.");
        
        OpenAPI openAPI = new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
                filterPaths(openAPI);
        return openAPI;
    }

    private void filterPaths(OpenAPI openAPI) {
        Paths paths = openAPI.getPaths();
        if (paths != null) {
            Map<String, io.swagger.v3.oas.models.PathItem> filteredPaths = paths.entrySet().stream()
                    .filter(entry -> !entry.getKey().startsWith("/api")) 
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                     paths.clear();
                     filteredPaths.forEach(paths::addPathItem);
        }
    }
}
