package com.project.saluyustore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class BasicAuthSecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
    private val unauthorizedHandler: JwtAuthEntryPoint,
    private val authenticationJwtTokenFilter: JwtAuthFilter,
    private val logoutHandler: LogoutHandler
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.disable() }.csrf { it.disable() }
            .exceptionHandling { it.authenticationEntryPoint(unauthorizedHandler) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.requestMatchers(*PATH_ARRAY).permitAll().anyRequest().authenticated() }
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.userDetailsService(userDetailsService)
        http.logout {
            it.logoutUrl("/api/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler { request, response, authentication ->
                    SecurityContextHolder.clearContext()
                }
        }
        return http.build()
    }

    companion object {
        //    private final TokenWhitelist tokenWhitelist;
        val PATH_ARRAY = arrayOf(
            "/api/auth/**",
            "/api/users",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs"
        )

    }
}
