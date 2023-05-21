package com.project.saluyustore.config

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            val userData: MasterUsers = userRepository.findByUserName(username)
//            val (_, userName, _, password) = userRepository.findByUserName(username)
            User(userData.userName, userData.password, ArrayList())
        } catch (e: Exception) {
            User("", "", ArrayList())
        }
    }
}
