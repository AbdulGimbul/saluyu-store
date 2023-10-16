package com.project.saluyustore.service.masterusers

import com.project.saluyustore.config.JwtService
import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
@Slf4j
class AuthServiceImpl(
    val masterUserRepository: MasterUserRepository,
    val authManager: AuthenticationManager,
    val jwtService: JwtService
) : AuthService {

    override fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse {

        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUserRequest.username,
                loginUserRequest.password
            )
        )

        val userByUsername = masterUserRepository.findFirstByUserName(loginUserRequest.username)
            .orElseThrow()

        val jwtToken = jwtService.generateToken(userByUsername)

        userByUsername.token = jwtToken
        userByUsername.tokenExpiredAt = next7Days()
        masterUserRepository.save(userByUsername)


        val userLoginResponse = UserLoginResponse(
            userId = userByUsername.userId,
            username = userByUsername.username,
            email = userByUsername.email,
            roleId = userByUsername.userRole,
            userToken = userByUsername.token
        )

        return userLoginResponse
    }

    private fun next7Days(): Long {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 7)
    }

    private fun findByIdOrThrowNotFound(userId: Int): MasterUsers {
        val user = masterUserRepository.findByIdOrNull(userId)
        if (user == null) {
            throw NotFoundException()
        } else {
            return user
        }
    }
}