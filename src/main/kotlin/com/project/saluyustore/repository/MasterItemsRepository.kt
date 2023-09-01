package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterProducts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterItemsRepository : JpaRepository<MasterProducts, String> {
}