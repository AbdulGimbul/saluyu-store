package com.project.saluyustore.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.bind.DefaultValue

data class CreateUserRequest(
    @field:NotBlank
    val username: String = "",
    @field:NotBlank
    val email: String = "",
    @field:NotBlank
    val password: String?,
    val userActive: Boolean?,
    @field:Schema(example = "3")
    val userRole: Int = 3,
)