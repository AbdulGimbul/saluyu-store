package com.project.saluyustore.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "masterUsers")
data class MasterUsers(
    @Id
    val userId: String,
    @Column(name = "userName")
    var userName: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "userActive")
    var userActive: Boolean? = true,
    @Column(name = "userRole")
    var userRole: Int = 2,
    @Column(name = "createdDate")
    val createdAt: Date,
    @Column(name = "createdBy")
    val createdBy: String,
    @Column(name = "modifyDate")
    var modifyDate: Date,
    @Column(name = "modifyBy")
    var modifyBy: String
)
