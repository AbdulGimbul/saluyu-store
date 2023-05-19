package com.project.saluyustore.model.request

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val userId: String?,
    @field:NotBlank
    val userName: String?,
    @field:NotBlank
    val email: String?,
    @field:NotBlank
    val password: String?,
    @field:NotBlank
    val userActive: Boolean?,
    @field:NotBlank
    val userRole: Int?,
)