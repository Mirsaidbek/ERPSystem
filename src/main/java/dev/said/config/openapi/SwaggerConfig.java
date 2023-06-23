package dev.said.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ERP System for ITIC ")
                        .description("This is an ERP System for ITIC to manage the personal and their information.")
                        .version("${api.version}")
                        .contact(new Contact()
                                .name("Mirsaid Karimullaev")
                                .email("mirsaidkarimullaev@gmail.com")
                                .url("https://t.me/mirsaidkarimullaev"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Production-Server")
//                        new Server().url("http://localhost:9090").description("Test-Server")
                ))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
//                        .addList("basicAuth"))
                .components(new Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .name("bearerAuth")
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT"))
                                .addSecuritySchemes("basicAuth",
                                        new SecurityScheme()
                                                .name("basicAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic"))
                );

    }

    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch(
                        "/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi auth() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch(
                        "/api/v1/auth/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi user() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch(
                        "/api/v1/user/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi admin() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch(
                        "/api/v1/admin/**"
                )
                .build();
    }


}

