package com.project.saluyustore.model.response

data class UserLoginResponse (
    val userId: String,
    val username: String,
    val email: String,
    val roleId: Int,
    val userToken: String?
)