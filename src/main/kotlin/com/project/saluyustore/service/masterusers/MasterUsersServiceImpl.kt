package com.project.saluyustore.service.masterusers

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.JwtTokenResponse
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.UserRepository
import com.project.saluyustore.util.JwtTokenUtil
import com.project.saluyustore.util.NotFoundException
import com.project.saluyustore.util.ValidationUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date
import java.util.stream.Collectors

@Service
class MasterUsersServiceImpl(
    val userRepository: UserRepository,
    val validationUtil: ValidationUtil,
    val authManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil
): MasterUsersService {

    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)

        val passwd = BCryptPasswordEncoder().encode(createUserRequest.password)

        val masterUsers = MasterUsers(
            userId = userRepository.getSeqUserId(),
            userName = createUserRequest.userName!!,
            email = createUserRequest.email!!,
            password = passwd,
            createdAt = Date(),
            createdBy = createUserRequest.userName,
            modifyDate = Date(),
            modifyBy = createUserRequest.userName,
        )

        userRepository.save(masterUsers)

        return convertUserToUserResponse(masterUsers)
    }

    override fun get(userId: String): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        return convertUserToUserResponse(user)
    }

    override fun update(userId: String, updateUserRequest: UpdateUserRequest): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        validationUtil.validate(updateUserRequest)

        user.apply {
            userName = updateUserRequest.userName!!
            email = updateUserRequest.email!!
            password = updateUserRequest.password!!
            modifyDate = Date()
            modifyBy = ""
        }

        userRepository.save(user)

        return convertUserToUserResponse(user)
    }

    override fun delete(userId: String) {
        val user = findByIdOrThrowNotFound(userId)
        userRepository.delete(user)
    }

    override fun list(listUserRequest: ListUserRequest): List<UserResponse> {
        val page = userRepository.findAll(PageRequest.of(listUserRequest.page, listUserRequest.size))
        val masterUsers: List<MasterUsers> = page.get().collect(Collectors.toList())
        return masterUsers.map { convertUserToUserResponse(it) }
    }

    override fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse {

        authManager.authenticate(UsernamePasswordAuthenticationToken(loginUserRequest.username, loginUserRequest.password))
        val userByUsername = userRepository.findByUserName(loginUserRequest.username)

        val jwtTokenResponse = JwtTokenResponse(
            userId = userByUsername.userId,
            username = userByUsername.userName,
            email = userByUsername.email,
            role = userByUsername.userRole
        )

        val userLoginResponse = UserLoginResponse(
            userId = userByUsername.userId,
            username = userByUsername.userName,
            email = userByUsername.email,
            roleId = userByUsername.userRole,
            userToken = jwtTokenUtil.generateToken(jwtTokenResponse, httpServletRequest)
        )

        return userLoginResponse
    }

    private fun findByIdOrThrowNotFound(userId: String): MasterUsers {
        val user = userRepository.findByIdOrNull(userId)
        if (user == null){
            throw NotFoundException()
        } else {
            return user
        }
    }

    private fun convertUserToUserResponse(masterUsers: MasterUsers): UserResponse{
        return UserResponse(
            userId = masterUsers.userId,
            userName = masterUsers.userName,
            email = masterUsers.email,
            password = masterUsers.password,
            userActive = masterUsers.userActive!!,
            userRole = masterUsers.userRole!!,
            createdAt = masterUsers.createdAt,
            createdBy = masterUsers.userName,
            modifyDate = masterUsers.modifyDate,
            modifyBy = ""
        )
    }
}