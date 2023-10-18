package com.project.saluyustore.repository

import com.project.saluyustore.entity.MasterUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MasterUserRepository : JpaRepository<MasterUser, Int> {
//    @Query(value = "select func_gen_seq_user_id()", nativeQuery = true)
//    fun getSeqUserId(): String

    //    fun findByUsername(username: String): MasterUsers
    fun findFirstByUserName(username: String): Optional<MasterUser>
    fun findFirstByEmail(email: String): Optional<MasterUser>
    fun findFirstByToken(token: String): Optional<MasterUser>
}