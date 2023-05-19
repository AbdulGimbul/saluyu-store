package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MasterCategoryRepository : JpaRepository<MasterCategory, Int> {
    @Query(value = "select nextval('generate_category_id_seq')", nativeQuery = true)
    fun getSeqCategoryId(): Int
}