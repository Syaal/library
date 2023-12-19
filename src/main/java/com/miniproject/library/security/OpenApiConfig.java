package com.miniproject.library.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpMethod;
import springfox.documentation.service.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                description = "OpenApi documentation for Library Management Service",
                title = "OpenApi for Library Management Service"
        ),
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth Description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

@Configuration
public class OpenApiConfig {
        @Bean
        public Docket docket() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.miniproject.library.controller"))
                        .paths(PathSelectors.any())
                        .build()
                        .useDefaultResponseMessages(false) //disable default respon
                        .globalResponses(HttpMethod.GET, globalResponses())
                        .globalResponses(HttpMethod.POST, globalResponses())
                        .globalResponses(HttpMethod.PUT, globalResponses())
                        .globalResponses(HttpMethod.DELETE, globalResponses());
        }

        private List<Response> globalResponses(){
                return Arrays.asList(
                        customResponse(200, "OK"),
                        customResponse(401, "Unauthorized"),
                        customResponse(403, "Forbidden")
                );
        }

        private Response customResponse(Integer code, String description) {
                return new ResponseBuilder()
                        .code(String.valueOf(code))
                        .description(description)
                        .build();
        }
}
