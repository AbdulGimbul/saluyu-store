package com.project.saluyustore.service.masterproduct

import com.project.saluyustore.config.ValidationUtil
import com.project.saluyustore.entity.MasterProduct
import com.project.saluyustore.model.request.CreateProductRequest
import com.project.saluyustore.model.request.UpdateProductRequest
import com.project.saluyustore.model.response.ProductResponse
import com.project.saluyustore.repository.MasterCategoryRepository
import com.project.saluyustore.repository.MasterProductRepository
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.HttpResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Transactional
class MasterProductServiceImp(
    private val masterProductRepository: MasterProductRepository,
    private val masterUserRepository: MasterUserRepository,
    private val masterCategoryRepository: MasterCategoryRepository,
    private val validationUtil: ValidationUtil
) : MasterProductService {

    override fun create(productRequest: CreateProductRequest): ProductResponse {
        validationUtil.validate(productRequest)

        val user = masterUserRepository.findById(productRequest.userId).orElseThrow {
            RuntimeException("User not found for ID ${productRequest.userId}")
        }

        val category = masterCategoryRepository.findById(productRequest.categoryId).orElseThrow {
            RuntimeException("Category not found for ID ${productRequest.categoryId}")
        }

        val masterProduct = MasterProduct(
            userId = user,
            productName = productRequest.productName,
            categoryId = category,
            unit = productRequest.unit,
            unitPrice = productRequest.unitPrice,
            productStock = productRequest.productStock,
            pictureProduct = productRequest.pictureProduct,
            updatedAt = java.util.Date()
        )

        masterProductRepository.save(masterProduct)

        return convertToProductResponse(masterProduct)
    }

    override fun getProducts(): ResponseEntity<*> {
        return try {
            val items = masterProductRepository.findAll();
            if (items.isEmpty()) {
                throw Exception("Items is empty")
            }
            val productResp = items.map { convertToProductResponse(it) }
            HttpResponse.setResp(productResp, "Success", productResp.size, HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    override fun update(productId: Int, updateProductRequest: UpdateProductRequest): ProductResponse {
        val product = masterProductRepository.findById(productId).orElseThrow {
            RuntimeException("Product not found for ID $productId")
        }

        product.apply {
            updateProductRequest.productName?.let { productName = it }
            updateProductRequest.categoryId?.let {
                val category = masterCategoryRepository.findById(it).orElseThrow {
                    RuntimeException("Category not found for ID $it")
                }
                categoryId = category
            }
            updateProductRequest.unit?.let { unit = it }
            updateProductRequest.unitPrice?.let { unitPrice = it }
            updateProductRequest.productStock?.let { productStock = it }
            updateProductRequest.pictureProduct?.let { pictureProduct = it }
        }

        masterProductRepository.save(product)

        return convertToProductResponse(product)
    }

    fun convertToProductResponse(masterProduct: MasterProduct): ProductResponse {
        return ProductResponse(
            user = masterProduct.userId?.userName, // assuming MasterUser has a userId field
            productId = masterProduct.productId,
            productName = masterProduct.productName,
            category = masterProduct.categoryId?.categoryDesc, // assuming MasterCategory has a categoryName field
            unit = masterProduct.unit,
            unitPrice = masterProduct.unitPrice,
            productStock = masterProduct.productStock.toString(),
            pictureProduct = masterProduct.pictureProduct,
            updatedAt = masterProduct.updatedAt
        )
    }
}