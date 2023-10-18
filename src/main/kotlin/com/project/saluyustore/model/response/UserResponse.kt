package com.project.saluyustore.model.response

import com.project.saluyustore.entity.Role
import java.util.*

data class UserResponse(

    val userId: Int,
    val fullName: String?,
    val username: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val profilePicture: String?,
    val userActive: Boolean,
    val userRole: Role,
    val createdAt: Date?,
    val createdBy: String,
    val modifiedAt: Date?,
    val modifiedBy: String
)
