package com.project.saluyustore.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val username: String = "",
    @field:NotBlank
    @field:Email
    val email: String = "",
    @field:NotBlank
    val password: String?,
    val userActive: Boolean?,
    @field:Schema(example = "3")
    val userRole: Int = 3,
)