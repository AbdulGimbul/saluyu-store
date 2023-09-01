package com.project.saluyustore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class BasicAuthSecurityConfig(
    private val userDetailsService: CustomUserDetailsService,
    private val unauthorizedHandler: JwtAuthEntryPoint,
    private val authenticationJwtTokenFilter: JwtAuthFilter
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
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
            .requestMatchers(*PATH_ARRAY).permitAll()
            .anyRequest().authenticated()
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.userDetailsService(userDetailsService)
        return http.build()
    }

    @Bean
    fun webSecuritySwagger(): WebSecurityCustomizer{
        return WebSecurityCustomizer { web: WebSecurity ->
            web.debug(securityDebug)
                .ignoring()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**")
        }
    }

    companion object {
        //    private final TokenWhitelist tokenWhitelist;
        val PATH_ARRAY = arrayOf(
            "/api/users/**"
        )
        const val securityDebug = false // Set your desired value for securityDebug

    }
}
