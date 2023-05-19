package com.project.saluyustore.service.mastercategory

import com.project.saluyustore.model.request.CategoryRequest
import org.springframework.http.ResponseEntity

interface MasterCategoryService {
    fun getCategory(): ResponseEntity<*>
    fun addCategory(categoryRequest: CategoryRequest): ResponseEntity<*>
}