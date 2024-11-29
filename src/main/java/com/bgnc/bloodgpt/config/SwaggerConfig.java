package com.bgnc.bloodgpt.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.*;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Info apiInfo() {
        return new Info()
                .title("BloodGPT API")
                .description("Blood test analysis using Artifical Intelligence .")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Buğra Onur Genç")
                        .email("bugraonurgenc@gmail.com")
                        .url("https://www.linkedin.com/in/bugra-onur-gen%C3%A7-3312b7209/"));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization")
                .in(In.HEADER)
                .description("Enter your Bearer token in the format **Bearer {token}**");
    }
}
