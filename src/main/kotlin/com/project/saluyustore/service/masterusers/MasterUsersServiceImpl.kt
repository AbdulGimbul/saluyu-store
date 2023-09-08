package com.project.saluyustore.service.masterusers

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.NotFoundException
import com.project.saluyustore.util.ValidationUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.stream.Collectors

@Service
class MasterUsersServiceImpl(
    val masterUserRepository: MasterUserRepository,
    val validationUtil: ValidationUtil
) : MasterUsersService {

    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)
        if (!masterUserRepository.findFirstByUsername(createUserRequest.username).isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email already registered")
        }

        val passwd = BCryptPasswordEncoder().encode(createUserRequest.password)

        val masterUsers = MasterUsers(
            username = createUserRequest.username,
            email = createUserRequest.email,
            password = passwd,
            createdAt = Date(),
            createdBy = createUserRequest.username,
            modifiedAt = Date(),
            modifiedBy = createUserRequest.username,
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
            username = updateUserRequest.username
            email = updateUserRequest.email
            password = updateUserRequest.password
            modifiedAt = Date()
            modifiedBy = ""
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

    private fun findByIdOrThrowNotFound(userId: Long): MasterUsers {
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
            modifiedBy = ""
        )
    }
}