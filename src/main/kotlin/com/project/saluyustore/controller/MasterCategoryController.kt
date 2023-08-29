package com.project.saluyustore.controller

import com.project.saluyustore.model.request.CategoryRequest
import com.project.saluyustore.service.mastercategory.MasterCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/master-category")
class MasterCategoryController(val masterCategoryService: MasterCategoryService) {
    @GetMapping("/get-category")
    fun getCategory(): ResponseEntity<*> {
        return masterCategoryService.getCategory()
    }

    @PostMapping("/add-category")
    fun addCategory(@RequestBody categoryRequest: CategoryRequest): ResponseEntity<*> {
        return masterCategoryService.addCategory(categoryRequest)
    }
}