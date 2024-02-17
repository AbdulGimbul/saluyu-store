package com.project.saluyustore.model.response

import java.util.*

data class ProductResponse(

    val user: String?,
    val productId: Int,
    val productName: String,
    val category: String?,
    val unit: String,
    val unitPrice: Long?,
    val productStock: String,
    val pictureProduct: String,
    val updatedAt: Date?
)
