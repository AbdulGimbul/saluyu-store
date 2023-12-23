package com.project.saluyustore.service.masterproduct

import com.project.saluyustore.model.request.CreateProductRequest
import com.project.saluyustore.model.request.UpdateProductRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.ProductResponse
import com.project.saluyustore.model.response.UserResponse
import org.springframework.http.ResponseEntity

interface MasterProductService {
    fun create(productRequest: CreateProductRequest): ProductResponse

    fun getProducts(): ResponseEntity<*>

    fun update(productId: Int, updateProductRequest: UpdateProductRequest): ProductResponse
}