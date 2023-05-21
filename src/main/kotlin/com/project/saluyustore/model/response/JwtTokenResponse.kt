package com.project.saluyustore.model.response

data class JwtTokenResponse (
    val userId: String? = null,
    val username: String? = null,
    val email: String? = null,
    val role: Int? = null
)