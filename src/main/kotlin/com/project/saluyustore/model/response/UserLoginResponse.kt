package com.project.saluyustore.model.response

import com.project.saluyustore.entity.Role

data class UserLoginResponse(
    val userId: Int,
    val username: String,
    val email: String,
    val roleId: Role,
    val userToken: String?
)