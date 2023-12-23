package com.project.saluyustore.model.request

import com.project.saluyustore.entity.Role
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateProductRequest(
    var productName: String,
    var userId: Int,
    var categoryId: Int,
    var unit: String,
    var unitPrice: Long,
    var productStock: Int,
    var pictureProduct: String,
)