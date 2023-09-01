package com.project.saluyustore.entity

import jakarta.persistence.*

@Entity
@Table(name = "master_category")
data class MasterCategories(
        @field:Id
        var categoryId: Int? = null,
        var categoryDesc: String? = null
)