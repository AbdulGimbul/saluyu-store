package com.project.saluyustore.service.masterusers

import com.project.saluyustore.config.ValidationUtil
import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class MasterUsersServiceImpl(
    val masterUserRepository: MasterUserRepository,
    val validationUtil: ValidationUtil
) : MasterUsersService {

    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)
        if (!masterUserRepository.findFirstByUserName(createUserRequest.username).isEmpty) {
            throw IllegalArgumentException("Username already registered")
        }

        if (!masterUserRepository.findFirstByEmail(createUserRequest.email).isEmpty) {
            throw IllegalArgumentException("Email already registered")
        }

        val passwd = BCryptPasswordEncoder().encode(createUserRequest.password)

        val masterUsers = MasterUsers(
            userName = createUserRequest.username,
            email = createUserRequest.email,
            userRole = createUserRequest.userRole,
            passwd = passwd,
            createdAt = Date(),
            createdBy = createUserRequest.username,
            modifiedAt = Date(),
            modifiedBy = createUserRequest.username,
            userActive = true
        )

        masterUserRepository.save(masterUsers)

        return convertUserToUserResponse(masterUsers)
    }

    override fun get(userId: Int): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        return convertUserToUserResponse(user)
    }

    override fun update(userId: Int, updateUserRequest: UpdateUserRequest): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        validationUtil.validate(updateUserRequest)

        user.apply {
            updateUserRequest.username?.let { userName = it }
            updateUserRequest.email?.let { email = it }
            updateUserRequest.password?.let { passwd = BCryptPasswordEncoder().encode(it) }
            modifiedAt = Date()
            modifiedBy = user.userName
        }

        masterUserRepository.save(user)

        return convertUserToUserResponse(user)
    }

    override fun delete(userId: Int) {
        val user = findByIdOrThrowNotFound(userId)
        masterUserRepository.delete(user)
    }

    override fun list(listUserRequest: ListUserRequest): List<UserResponse> {
        val page = masterUserRepository.findAll(PageRequest.of(listUserRequest.page, listUserRequest.size))
        val masterUsers: List<MasterUsers> = page.get().collect(Collectors.toList())
        return masterUsers.map { convertUserToUserResponse(it) }
    }

    private fun findByIdOrThrowNotFound(userId: Int): MasterUsers {
        val user = masterUserRepository.findByIdOrNull(userId)
        if (user == null) {
            throw NotFoundException()
        } else {
            return user
        }
    }

    private fun convertUserToUserResponse(masterUsers: MasterUsers): UserResponse {
        return UserResponse(
            userId = masterUsers.userId,
            username = masterUsers.username,
            email = masterUsers.email,
            userActive = masterUsers.userActive,
            userRole = masterUsers.userRole,
            createdAt = masterUsers.createdAt,
            createdBy = masterUsers.username,
            modifiedAt = masterUsers.modifiedAt,
            modifiedBy = masterUsers.modifiedBy
        )
    }
}