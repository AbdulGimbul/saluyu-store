package com.project.saluyustore.model.response

import java.util.*

data class UserResponse(

    val userId: Long?,
    val userName: String,
    val email: String,
    val password: String,
    val userActive: Boolean,
    val userRole: Int,
    val createdAt: Date,
    val createdBy: String,
    val modifyDate: Date,
    val modifyBy: String?
)
