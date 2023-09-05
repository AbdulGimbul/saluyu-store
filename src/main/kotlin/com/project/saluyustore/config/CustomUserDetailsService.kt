package com.project.saluyustore.config

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.repository.MasterUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val masterUserRepository: MasterUserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            val userData: MasterUsers = masterUserRepository.findByUsername(username)
            User(userData.username, userData.password, ArrayList())
        } catch (e: Exception) {
            User("", "", ArrayList())
        }
    }
}
