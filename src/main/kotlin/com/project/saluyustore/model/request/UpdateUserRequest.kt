package com.project.saluyustore.model.request

import jakarta.validation.constraints.Email

data class UpdateUserRequest(
    val fullName: String? = null,
    val username: String? = null,
    @field:Email
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val profilePicture: String? = null,
    val password: String? = null
)