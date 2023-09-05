package com.project.saluyustore.service.masterusers

import com.project.saluyustore.repository.MasterUserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val masterUserRepository: MasterUserRepository
) : LogoutHandler {

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val header = request?.getHeader("Authorization")
        var token: String? = null
        if (header != null && header.startsWith("Bearer ")) {
            token = header.replace("Bearer ", "")
            val storedToken = masterUserRepository.findFirstByToken(token)
                .orElse(null)
            if (storedToken != null){
                storedToken.tokenExpiredAt = null
                storedToken.token = null
                masterUserRepository.save(storedToken)
            }
        }

    }
}