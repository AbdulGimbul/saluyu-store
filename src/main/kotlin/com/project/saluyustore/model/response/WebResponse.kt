package com.project.saluyustore.model.response

data class WebResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)
