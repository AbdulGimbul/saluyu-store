package com.project.saluyustore.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        contact = Contact(
            name = "saluyustore",
            email = "saluyustore@example.com",
            url = "https://salutustore.test"
        ),
        description = "Service api hasil bumi saluyustore",
        title = "Spesification - Saluyu Store API",
        version = "0.0.1",
        license = License(
            name = "Copyright@2023",
            url = "https://license.test",
        ),
        termsOfService = "Terms of services"
    ),
    security = [
        SecurityRequirement(
            name = "bearerAuth"
        )
    ]
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth token",
    scheme = "Bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
class OpenApiConfig