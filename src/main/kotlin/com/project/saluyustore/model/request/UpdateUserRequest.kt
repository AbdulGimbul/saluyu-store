package com.project.saluyustore.model.request

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdateUserRequest(

    @field:NotBlank
    val userName: String = "",

    @field:NotBlank
    val email: String = "",

    @field:NotBlank
    val password: String = "",

    @field:NotNull
    val userActive: Boolean? = true,

    @field:NotNull
    val userRole: Int? = 2,
)