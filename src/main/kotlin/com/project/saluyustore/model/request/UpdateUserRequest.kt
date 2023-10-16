package com.project.saluyustore.model.request

data class UpdateUserRequest(

    var username: String? = null,

    val email: String? = null,

    val password: String? = null,

    val userActive: Boolean? = null,

    val userRole: Int? = null,
)