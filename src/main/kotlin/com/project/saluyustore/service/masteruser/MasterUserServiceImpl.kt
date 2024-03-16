package com.project.saluyustore.service.masteruser

import com.project.saluyustore.config.ValidationUtil
import com.project.saluyustore.entity.MasterUser
import com.project.saluyustore.entity.MasterUserDetail
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.MasterUserDetailRepository
import com.project.saluyustore.repository.MasterUserRepository
import com.project.saluyustore.util.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class MasterUserServiceImpl(
    val userRepository: MasterUserRepository,
    val userDetailRepository: MasterUserDetailRepository,
    val validationUtil: ValidationUtil
) : MasterUserService {

    @Transactional
    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)
        if (!userRepository.findFirstByUserName(createUserRequest.username).isEmpty) {
            throw IllegalArgumentException("Username already registered")
        }

        if (!userRepository.findFirstByEmail(createUserRequest.email).isEmpty) {
            throw IllegalArgumentException("Email already registered")
        }

        val passwd = BCryptPasswordEncoder().encode(createUserRequest.password)

        val masterUser = MasterUser(
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

        userRepository.save(masterUser)

        val userDetail = MasterUserDetail(
            userId = masterUser.userId
        )

        userDetailRepository.save(userDetail)

        return convertUserToUserResponse(masterUser)
    }

    override fun get(userId: Int): UserResponse {
        val user = findByIdOrThrowNotFound(userId)
        return convertUserToUserResponse(user)
    }

    override fun delete(userId: Int) {
        val user = findByIdOrThrowNotFound(userId)
        userRepository.delete(user)
    }

    @Transactional
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

        userRepository.save(user)

        val detailUser = userDetailRepository.findById(userId).orElse(null)
        detailUser?.apply {
            updateUserRequest.fullName?.let { fullName = it }
            updateUserRequest.address?.let { address = it }
            updateUserRequest.phoneNumber?.let { phoneNumber = it }
        }

        userDetailRepository.save(detailUser)

        return convertUserToUserResponse(user)
    }

    override fun list(listUserRequest: ListUserRequest): List<UserResponse> {
        val page = userRepository.findAll(PageRequest.of(listUserRequest.page, listUserRequest.size))
        val masterUsers: List<MasterUser> = page.get().collect(Collectors.toList())
        return masterUsers.map { convertUserToUserResponse(it) }
    }

    private fun findByIdOrThrowNotFound(userId: Int): MasterUser {
        val user = userRepository.findByIdOrNull(userId)
        if (user == null) {
            throw NotFoundException()
        } else {
            return user
        }
    }

    private fun convertUserToUserResponse(user: MasterUser): UserResponse {
        val userDetail = userDetailRepository.findById(user.userId)
            .orElseThrow { RuntimeException("User detail not found for user ID ${user.userId}") }
        return UserResponse(
            userId = user.userId,
            fullName = userDetail.fullName,
            username = user.username,
            email = user.email,
            phoneNumber = userDetail.phoneNumber,
            address = userDetail.address,
            profilePicture = userDetail.profilePicture,
            userActive = user.userActive,
            userRole = user.userRole,
            createdAt = user.createdAt,
            createdBy = user.username,
            modifiedAt = user.modifiedAt,
            modifiedBy = user.modifiedBy
        )
    }
}