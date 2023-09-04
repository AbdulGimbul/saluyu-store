package com.project.saluyustore.service.masterusers

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.JwtTokenResponse
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.JwtTokenUtil
import com.project.saluyustore.util.NotFoundException
import com.project.saluyustore.util.ValidationUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.Date
import java.util.stream.Collectors

@Service
class MasterUsersServiceImpl(
    val masterUserRepository: MasterUserRepository,
    val validationUtil: ValidationUtil,
    val authManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil
): MasterUsersService {

    @Transactional
    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)
        if (masterUserRepository.findByUsername(createUserRequest.username).userId != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered")
        }

        val passwd = BCryptPasswordEncoder().encode(createUserRequest.password)

        val masterUsers = MasterUsers(
            username = createUserRequest.username,
            email = createUserRequest.email,
            password = passwd,
            createdAt = Date(),
            createdBy = createUserRequest.username,
            modifyDate = Date(),
            modifyBy = createUserRequest.username,
        )

        masterUserRepository.save(masterUsers)

        return convertUserToUserResponse(masterUsers)
    }

    override fun get(userId: Long): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        return convertUserToUserResponse(user)
    }

    override fun update(userId: Long, updateUserRequest: UpdateUserRequest): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        validationUtil.validate(updateUserRequest)

        user.apply {
            username = updateUserRequest.userName
            email = updateUserRequest.email
            password = updateUserRequest.password
            modifyDate = Date()
            modifyBy = ""
        }

        masterUserRepository.save(user)

        return convertUserToUserResponse(user)
    }

    override fun delete(userId: Long) {
        val user = findByIdOrThrowNotFound(userId)
        masterUserRepository.delete(user)
    }

    override fun list(listUserRequest: ListUserRequest): List<UserResponse> {
        val page = masterUserRepository.findAll(PageRequest.of(listUserRequest.page, listUserRequest.size))
        val masterUsers: List<MasterUsers> = page.get().collect(Collectors.toList())
        return masterUsers.map { convertUserToUserResponse(it) }
    }

    override fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse {

        authManager.authenticate(UsernamePasswordAuthenticationToken(loginUserRequest.username, loginUserRequest.password))
        val userByUsername = masterUserRepository.findByUsername(loginUserRequest.username)

        val jwtTokenResponse = JwtTokenResponse(
            userId = userByUsername.userId,
            username = userByUsername.username,
            email = userByUsername.email,
            role = userByUsername.userRole
        )

        val userLoginResponse = UserLoginResponse(
            userId = userByUsername.userId,
            username = userByUsername.username,
            email = userByUsername.email,
            roleId = userByUsername.userRole,
            userToken = jwtTokenUtil.generateToken(jwtTokenResponse, httpServletRequest)
        )

        return userLoginResponse
    }

    private fun findByIdOrThrowNotFound(userId: Long): MasterUsers {
        val user = masterUserRepository.findByIdOrNull(userId)
        if (user == null){
            throw NotFoundException()
        } else {
            return user
        }
    }

    private fun convertUserToUserResponse(masterUsers: MasterUsers): UserResponse{
        return UserResponse(
            userId = masterUsers.userId,
            userName = masterUsers.username,
            email = masterUsers.email,
            userActive = masterUsers.userActive,
            userRole = masterUsers.userRole,
            createdAt = masterUsers.createdAt,
            createdBy = masterUsers.username,
            modifyDate = masterUsers.modifyDate,
            modifyBy = ""
        )
    }
}