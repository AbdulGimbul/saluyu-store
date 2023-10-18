package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterItemsRepository : JpaRepository<MasterProduct, String> {
}