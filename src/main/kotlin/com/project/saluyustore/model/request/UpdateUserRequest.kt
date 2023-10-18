package com.project.saluyustore.model.request

data class UpdateUserRequest(
    val fullName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val password: String? = null
)