package com.project.saluyustore.model.response

import com.project.saluyustore.entity.Role
import java.util.*

data class UserResponse(

    val userId: Int,
    val username: String,
    val email: String,
    val userActive: Boolean,
    val userRole: Role,
    val createdAt: Date?,
    val createdBy: String,
    val modifiedAt: Date?,
    val modifiedBy: String?
)
