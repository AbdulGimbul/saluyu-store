package com.project.saluyustore.model.request

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @field:NotBlank
    val username: String = "",
    @field:NotBlank
    val email: String = "",
    @field:NotBlank
    val password: String?,
    val userActive: Boolean?,
    val userRole: Int?,
)