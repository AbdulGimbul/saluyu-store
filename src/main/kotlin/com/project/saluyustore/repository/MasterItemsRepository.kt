package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterItems
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterItemsRepository : JpaRepository<MasterItems, String> {
}