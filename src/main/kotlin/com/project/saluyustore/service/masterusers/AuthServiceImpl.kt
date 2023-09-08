package com.project.saluyustore.service.masterusers

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.JwtTokenResponse
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.JwtTokenUtil
import com.project.saluyustore.util.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    val masterUserRepository: MasterUserRepository,
    val authManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil,
) : AuthService {

    override fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse {

        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUserRequest.username,
                loginUserRequest.password
            )
        )
        val userByUsername = masterUserRepository.findByUsername(loginUserRequest.username)

        val jwtTokenResponse = JwtTokenResponse(
            userId = userByUsername.userId,
            username = userByUsername.username,
            email = userByUsername.email,
            role = userByUsername.userRole
        )

        userByUsername.token = jwtTokenUtil.generateToken(jwtTokenResponse, httpServletRequest)
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

    override fun logout(userId: Long) {
        val user = findByIdOrThrowNotFound(userId)
        user.token = null
        user.tokenExpiredAt = null

        masterUserRepository.save(user)
    }

    private fun next7Days(): Long {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 7)
    }

    private fun findByIdOrThrowNotFound(userId: Long): MasterUsers {
        val user = masterUserRepository.findByIdOrNull(userId)
        if (user == null) {
            throw NotFoundException()
        } else {
            return user
        }
    }
}