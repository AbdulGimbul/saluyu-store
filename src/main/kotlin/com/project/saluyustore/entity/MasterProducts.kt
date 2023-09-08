package com.project.saluyustore.entity

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "master_product")
data class MasterProducts(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.SEQUENCE)
    @field:Column(name = "item_id")
    var itemId: Long? = null,
    @field:Column(name = "item_name")
    var itemName: String? = null,
    var category: Int? = null,
    var unit: String? = null,
    @field:Column(name = "unit_price")
    var unitPrice: Long? = null,
    @field:Column(name = "item_stock")
    var itemStock: Int? = null,
    @field:Column(name = "picture_item")
    var pictureItem: String? = null,
    @field:Column(name = "updated_at")
    var updatedAt: Date? = null,
    @field:Column(name = "user_id")
    var userId: String? = null
)