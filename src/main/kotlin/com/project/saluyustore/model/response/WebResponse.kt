package com.project.saluyustore.model.response

data class WebResponse<T>(
    val code: Int? = null,
    val errors: String? = null,
    val data: T? = null
)
