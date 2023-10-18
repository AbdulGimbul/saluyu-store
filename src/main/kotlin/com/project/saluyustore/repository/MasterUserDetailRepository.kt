package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterUserDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterUserDetailRepository : JpaRepository<MasterUserDetail, Int>