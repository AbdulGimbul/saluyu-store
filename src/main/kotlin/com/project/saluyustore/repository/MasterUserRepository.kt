package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MasterUserRepository : JpaRepository<MasterUsers, Long> {
//    @Query(value = "select func_gen_seq_user_id()", nativeQuery = true)
//    fun getSeqUserId(): String

    fun findByUsername(username: String): MasterUsers
}