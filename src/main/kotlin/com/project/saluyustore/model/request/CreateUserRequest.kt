package com.project.saluyustore.model.request

import com.project.saluyustore.entity.Role
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "Username must not be blank")
    var username: String = "",
    @field:NotBlank(message = "Email must not be blank")
    @field:Email
    var email: String = "",
    @field:NotBlank(message = "Password must not be blank")
    var password: String= "",
    var userActive: Boolean = true,
    @field:Schema(example = "2")
    var userRole: Role = Role.BUYER,
)