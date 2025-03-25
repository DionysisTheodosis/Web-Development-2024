package com.icsd.healthcare.shared.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Dionysis Theodosis"
                ),
                description = "OpenApi documentation for HealthCare-WebDevelopment Project 2024",
                title = "OpenApi specification - HealthCare - Dionysis Theodosis"
        ),
        servers = {
                @Server(
                        description ="Local ENV",
                        url = "http://backend:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "SESSION"
                )
        }
)
@SecurityScheme(

        name = "SESSION",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "SESSION",
        description = "Session Cookie for Authentication"
)
public class OpenApiConfig {

}
