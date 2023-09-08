package com.project.saluyustore.model.response

import java.util.*

data class UserResponse(

    val userId: Long?,
    val username: String,
    val email: String,
    val userActive: Boolean,
    val userRole: Int,
    val createdAt: Date,
    val createdBy: String,
    val modifiedAt: Date,
    val modifiedBy: String?
)
