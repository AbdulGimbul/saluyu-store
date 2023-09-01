package com.project.saluyustore.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "master_user")
data class MasterUsers(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    val userId: Long? = null,
    var username: String,
    var email: String,
    var password: String,
    @Column(name = "user_active")
    var userActive: Boolean? = true,
    @Column(name = "user_role")
    var userRole: Int = 2,
    @Column(name = "created_at")
    val createdAt: Date,
    @Column(name = "created_by")
    val createdBy: String,
    @Column(name = "modify_date")
    var modifyDate: Date,
    @Column(name = "modify_by")
    var modifyBy: String
)
