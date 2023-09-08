package com.project.saluyustore.entity

import jakarta.persistence.*

@Entity
@Table(name = "master_category")
data class MasterCategories(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.SEQUENCE)
    @field:Column(name = "category_id")
    var categoryId: Long? = null,
    @field:Column(name = "category_desc")
    var categoryDesc: String? = null
)