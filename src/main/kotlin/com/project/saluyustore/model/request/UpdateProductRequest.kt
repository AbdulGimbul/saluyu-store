package com.project.saluyustore.model.request

import jakarta.validation.constraints.Email

data class UpdateProductRequest(
    val productName: String? = null,
    var categoryId: Int? = null,
    var unit: String? = null,
    var unitPrice: Long? = null,
    var productStock: Int? = null,
    var pictureProduct: String? = null,
)