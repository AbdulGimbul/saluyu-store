package com.project.saluyustore.entity

import com.project.saluyustore.model.response.CategoryResponse
import com.project.saluyustore.model.response.ProductResponse
import com.project.saluyustore.model.response.ProductUserResponse
import jakarta.persistence.*

@Entity
@Table(name = "master_product")
data class MasterProduct(
    @field:Id
    @field:GeneratedValue
    @field:Column(name = "product_id")
    var productId: Int = 0,
    @field:Column(name = "product_name")
    var productName: String = "",
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "category_id")
    var categoryId: MasterCategory? = null,
    var unit: String = "",
    @field:Column(name = "unit_price")
    var unitPrice: Long? = null,
    @field:Column(name = "product_stock")
    var productStock: Int = 0,
    @field:Column(name = "picture_product")
    var pictureProduct: String = "",
    @field:Column(name = "updated_at")
    var updatedAt: java.util.Date? = null,
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id")
    var userId: MasterUser? = null
)

fun MasterProduct.toProductResponse(): ProductResponse {
    return ProductResponse(
        user = ProductUserResponse(
            userId = userId?.userId ?: 0,
            fullName = userId?.detailUser?.fullName ?: "",
            username = userId?.userName ?: "",
            email = userId?.email ?: "",
            phoneNumber = userId?.detailUser?.phoneNumber,
            address = userId?.detailUser?.address,
            profilePicture = userId?.detailUser?.profilePicture,
            userActive = userId?.userActive ?: false,
            userRole = userId?.userRole ?: Role.BUYER
        ),
        productId = productId,
        productName = productName,
        category = CategoryResponse(
            categoryId = categoryId?.categoryId,
            categoryDesc = categoryId?.categoryDesc
        ),
        unit = unit,
        unitPrice = unitPrice,
        productStock = productStock.toString(),
        pictureProduct = pictureProduct,
        updatedAt = updatedAt
    )
}