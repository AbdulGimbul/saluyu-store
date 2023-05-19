package com.project.saluyustore.entity

import jakarta.persistence.*

@Entity
@Table(name = "masterCategory")
data class MasterCategory(
        @Id
        var categoryId: Int? = null,
        var categoryDesc: String? = null
)