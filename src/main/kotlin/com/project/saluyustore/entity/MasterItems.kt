package com.project.saluyustore.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "masterItems")
data class MasterItems(
        @Id
        var itemId: String? = null,
        var itemName: String? = null,
        var category: Int? = null,
        var unit: String? = null,
        var unitPrice: Long? = null,
        var itemStock: Int? = null,
        var pictureItem: String? = null,
        var updatedDate: Date? = null,
        var userId: String? = null
)