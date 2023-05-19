package com.project.saluyustore.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "masterUsers")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: String,
    @Column(name = "userName")
    var userName: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "userActive")
    var userActive: Boolean,
    @Column(name = "userRole")
    var userRole: Int,
    @Column(name = "createdDate")
    val createdAt: Date,
    @Column(name = "createdBy")
    val createdBy: String,
    @Column(name = "modifyDate")
    var modifyDate: Date,
    @Column(name = "modifyBy")
    var modifyBy: String
)
