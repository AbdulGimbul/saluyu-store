package com.project.saluyustore.config

import com.project.saluyustore.model.response.JwtTokenResponse
import com.project.saluyustore.util.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthFilter(private val jwtTokenUtil: JwtTokenUtil, private val userDetailsService: UserDetailsService) :
    OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")
        var username: String? = null
        var token: String? = null
        var jwtTokenDto: JwtTokenResponse? = JwtTokenResponse()
        if (header != null && header.startsWith("Bearer ")) {
            token = header.replace("Bearer ", "")
            try {
                jwtTokenDto = jwtTokenUtil.extractToken(token)
                username = jwtTokenDto!!.username
            } catch (e: IllegalArgumentException) {
                logger.error("Username tidak ditemukan")
            } catch (e: ExpiredJwtException) {
                logger.warn("Token Expired")
            } catch (e: SignatureException) {
                logger.error("Username atau password tidak sesuai")
            }
        } else {
            logger.warn("Header tidak di set / tidak menemukan kata Bearer ")
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if (jwtTokenUtil.validationToken(token, userDetails)) {
                val authenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        request.setAttribute("User-Data", jwtTokenDto)
        filterChain.doFilter(request, response)
    }
}
