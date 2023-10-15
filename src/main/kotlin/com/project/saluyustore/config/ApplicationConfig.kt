package com.project.saluyustore.config

import com.project.saluyustore.repository.MasterUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class ApplicationConfig(
    private val masterUserRepository: MasterUserRepository
) {

    @Bean
    fun userDetailService(): UserDetailsService {
        return UserDetailsService { username -> masterUserRepository.findFirstByUserName(username)
            .orElseThrow { UsernameNotFoundException("User Not Found") }}
    }

    @Bean
    fun authProvider() : AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService())
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider
    }

    @Bean
    fun authManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}