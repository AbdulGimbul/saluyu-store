package com.project.saluyustore.service.masteritems

import org.springframework.http.ResponseEntity

interface MasterItemsService {
    fun getItems(): ResponseEntity<*>
}