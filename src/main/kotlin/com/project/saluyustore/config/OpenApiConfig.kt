package com.project.saluyustore.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun swaggerConfiguration(): OpenAPI {
        val openAPI = OpenAPI()
        openAPI.info = Info().title("Saluyu Store API")
            .description("Bismillah")
            .version("0.0.1")

        return openAPI
    }
}