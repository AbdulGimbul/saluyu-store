package com.project.saluyustore.entity

import jakarta.persistence.*
import java.sql.Date

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