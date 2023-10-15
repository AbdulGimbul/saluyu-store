package com.project.saluyustore.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdateUserRequest(

    @field:NotBlank
    var username: String = "",

    @field:NotBlank
    val email: String = "",

    @field:NotBlank
    val password: String = "",

    @field:NotNull
    val userActive: Boolean? = true,

    @field:NotNull
    val userRole: Int? = 2,
)