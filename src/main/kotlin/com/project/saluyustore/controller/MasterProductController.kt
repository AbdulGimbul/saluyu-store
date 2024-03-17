package com.project.saluyustore.controller

import com.project.saluyustore.model.request.CreateProductRequest
import com.project.saluyustore.model.request.UpdateProductRequest
import com.project.saluyustore.service.masterproduct.MasterProductService
import com.project.saluyustore.util.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class MasterProductController(private val masterProductService: MasterProductService) {

    @PostMapping
    fun create(@RequestBody body: CreateProductRequest): ResponseEntity<*> {
        return try {
            val productResponse = masterProductService.create(body)

            HttpResponse.setResp(data = productResponse, message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun getData(): ResponseEntity<*> {
        return masterProductService.getProducts()
    }

    @PutMapping("/{productId}")
    fun update(
        @PathVariable("productId") productId: Int,
        @RequestBody body: UpdateProductRequest
    ): ResponseEntity<*> {
        return try {
            val productResponse = masterProductService.update(productId, body)

            HttpResponse.setResp(data = productResponse, message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/init-data")
    fun initData(): ResponseEntity<*> {
        return try {
            val productResponse = masterProductService.initData()

            HttpResponse.setResp(data = productResponse, message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }
}