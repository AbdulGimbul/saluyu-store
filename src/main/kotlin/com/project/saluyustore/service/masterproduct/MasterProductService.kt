package com.project.saluyustore.service.masterproduct

import org.springframework.http.ResponseEntity

interface MasterProductService {
    fun getItems(): ResponseEntity<*>
}