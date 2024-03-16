package com.project.saluyustore.model.response

import java.util.*

data class ProductResponse(
    val user: ProductUserResponse?,
    val productId: Int,
    val productName: String,
    val category: CategoryResponse?,
    val unit: String,
    val unitPrice: Long?,
    val productStock: String,
    val pictureProduct: String,
    val updatedAt: Date?
)
