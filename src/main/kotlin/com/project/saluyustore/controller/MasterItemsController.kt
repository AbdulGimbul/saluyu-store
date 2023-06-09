package com.project.saluyustore.controller

import com.project.saluyustore.service.masteritems.MasterItemsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/master-items")
class MasterItemsController(private val masterItemsService: MasterItemsService) {
    @GetMapping("/get-data")
    fun getData(): ResponseEntity<*> {
        return masterItemsService.getData()
    }
}