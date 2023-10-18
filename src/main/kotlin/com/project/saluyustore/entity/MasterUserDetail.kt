package com.project.saluyustore.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "master_user_detail")
data class MasterUserDetail(
    @Id
    @Column(name = "user_id")
    val userId: Int = 0,
    @Column(name = "full_name")
    var fullName: String = "",
    var address: String = "",
    var profile_picture: String = "",
    @Column(name = "phone_number")
    var phoneNumber: String = "",
    @Column(name = "modified_at")
    var modifiedAt: Date? = null,
    @Column(name = "modified_by")
    var modifiedBy: String = "",
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    var masterUser: MasterUser? = null
)