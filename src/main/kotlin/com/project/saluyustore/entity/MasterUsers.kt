package com.project.saluyustore.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "master_user")
data class MasterUsers(
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    val userId: Int = 0,
    @Column(name = "username")
    var userName: String = "",
    var email: String = "",
    @Column(name = "password")
    var passwd: String = "",
    @Column(name = "user_active")
    var userActive: Boolean = false,
    @Column(name = "user_role")
    @Enumerated(EnumType.ORDINAL)
    var userRole: Role = Role.SUPPLIER,
    @Column(name = "created_at")
    val createdAt: Date? = null,
    @Column(name = "created_by")
    val createdBy: String = "",
    @Column(name = "modified_at")
    var modifiedAt: Date? = null,
    @Column(name = "modified_by")
    var modifiedBy: String = "",
    var token: String? = null,
    @Column(name = "token_expired_at")
    var tokenExpiredAt: Long? = null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(userRole.name))
    }

    override fun getPassword(): String {
        return passwd
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
