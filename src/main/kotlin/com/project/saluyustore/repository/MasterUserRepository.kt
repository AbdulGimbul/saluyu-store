package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MasterUserRepository : JpaRepository<MasterUsers, Long> {
//    @Query(value = "select func_gen_seq_user_id()", nativeQuery = true)
//    fun getSeqUserId(): String

    fun findByUsername(username: String): MasterUsers
    fun findFirstByUsername(username: String): Optional<MasterUsers>
    fun findFirstByToken(token: String): Optional<MasterUsers>
}