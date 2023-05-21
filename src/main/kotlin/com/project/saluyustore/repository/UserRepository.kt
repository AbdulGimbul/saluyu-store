package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<MasterUsers, String> {
    @Query(value = "select func_gen_seq_user_id()", nativeQuery = true)
    fun getSeqUserId(): String

    fun findByUserName(username: String): MasterUsers
}