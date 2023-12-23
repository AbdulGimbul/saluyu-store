package com.project.saluyustore.model.response

import com.project.saluyustore.entity.Role

data class UserLoginResponse(
    val userId: Int,
    val fullName: String?,
    val username: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val profilePicture: String?,
    val userRole: Role,
    val userToken: String?
)