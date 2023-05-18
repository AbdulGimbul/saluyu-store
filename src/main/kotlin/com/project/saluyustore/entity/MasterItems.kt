package com.project.saluyustore.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.sql.Date

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "masterItems")
class MasterItems {
    @Id
    private val itemId: String? = null
    private val itemName: String? = null
    private val category: Int? = null
    private val unit: String? = null
    private val unitPrice: Long? = null
    private val itemStock: Int? = null
    private val pictureItem: String? = null
    private val updatedDate: Date? = null
    private val userId: String? = null

}