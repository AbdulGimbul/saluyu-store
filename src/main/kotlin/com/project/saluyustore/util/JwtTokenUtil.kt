package com.project.saluyustore.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.project.saluyustore.model.response.JwtTokenResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.CompressionCodecs
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtil(private val objectMapper: ObjectMapper) {

    private val secret = "saluyukey"

    fun doGenerateToken(claims: Map<String, Any?>?, jwtTokenDto: JwtTokenResponse?): String? {
        return try {
            Jwts.builder().setClaims(claims)
                .setSubject(objectMapper.writeValueAsString(jwtTokenDto))
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 3600000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compressWith(CompressionCodecs.GZIP)
                .compact()
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            null
        }
    }

    fun generateToken(jwtTokenDto: JwtTokenResponse?, request: HttpServletRequest?): String? {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["ip"] = getClientIp(request)
        claims["ua"] = getUserAgent(request)
        return doGenerateToken(claims, jwtTokenDto)
    }

    fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    fun <T> getClaimsFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getExpirationDateFromToken(token: String?): Date {
        return getClaimsFromToken(token) { obj: Claims -> obj.expiration }
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun extractToken(token: String?): JwtTokenResponse? {
        return try {
            val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            if (Date().after(claims.body.expiration)) {
                null
            } else objectMapper.readValue(claims.body.subject, JwtTokenResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun validationToken(token: String?, userDetails: UserDetails): Boolean {
        val jwtTokenDto = extractToken(token)
        val userName = jwtTokenDto!!.username
        return userName == userDetails.username && !isTokenExpired(token)
    }

    companion object {
        private const val JWT_TOKEN_VALIDITY = (24 * 365).toLong()
        private fun getClientIp(request: HttpServletRequest?): String? {
            var remoteAddr: String? = ""
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR")
                if (remoteAddr == null || "" == remoteAddr) {
                    remoteAddr = request.remoteAddr
                }
            }
            return remoteAddr
        }

        private fun getUserAgent(request: HttpServletRequest?): String {
            var ua = ""
            if (request != null) {
                ua = request.getHeader("User-Agent")
            }
            return ua
        }
    }
}
