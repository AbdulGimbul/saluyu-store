package com.project.saluyustore.controller

import com.project.saluyustore.service.masterproduct.MasterProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/master-items")
class MasterProductController(private val masterProductService: MasterProductService) {
    @GetMapping("/get-items")
    fun getData(): ResponseEntity<*> {
        return masterProductService.getItems()
    }
}