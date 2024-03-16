package com.project.saluyustore.model.response

import com.project.saluyustore.entity.Role

/**
 * @author  : Irsan Ramadhan
 * @email   : irsan.ramadhan@iconpln.co.id
 * @date    : 18/02/2024
 */
data class ProductUserResponse(
    val userId: Int,
    val fullName: String?,
    val username: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val profilePicture: String?,
    val userActive: Boolean,
    val userRole: Role,
)
