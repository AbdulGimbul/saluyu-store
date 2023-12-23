package com.project.saluyustore.service.masteruser

import com.project.saluyustore.config.JwtService
import com.project.saluyustore.entity.MasterUser
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.repository.MasterUserDetailRepository
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
    val userRepository: MasterUserRepository,
    val userDetailRepository: MasterUserDetailRepository,
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

        val userByUsername = userRepository.findFirstByUserName(loginUserRequest.username)
            .orElseThrow()

        val jwtToken = jwtService.generateToken(userByUsername)

        userByUsername.token = jwtToken
        userByUsername.tokenExpiredAt = next7Days()
        userRepository.save(userByUsername)

        val userDetail = userDetailRepository.findById(userByUsername.userId).orElseThrow()

        val userLoginResponse = UserLoginResponse(
            userId = userByUsername.userId,
            fullName = userDetail.fullName,
            username = userByUsername.username,
            email = userByUsername.email,
            phoneNumber = userDetail.phoneNumber,
            address = userDetail.address,
            profilePicture = userDetail.profile_picture,
            userRole = userByUsername.userRole,
            userToken = userByUsername.token
        )

        return userLoginResponse
    }

    private fun next7Days(): Long {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 7)
    }

    private fun findByIdOrThrowNotFound(userId: Int): MasterUser {
        val user = userRepository.findByIdOrNull(userId)
        if (user == null) {
            throw NotFoundException()
        } else {
            return user
        }
    }
}