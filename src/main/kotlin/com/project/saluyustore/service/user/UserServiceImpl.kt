package com.project.saluyustore.service.user

import com.project.saluyustore.entity.User
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.repository.UserRepository
import com.project.saluyustore.util.NotFoundException
import com.project.saluyustore.util.ValidationUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Date
import java.util.stream.Collectors

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val validationUtil: ValidationUtil
): UserService {

    override fun create(createUserRequest: CreateUserRequest): UserResponse {
        validationUtil.validate(createUserRequest)

        val user = User(
            userId = createUserRequest.userId!!,
            userName = createUserRequest.userName!!,
            email = createUserRequest.email!!,
            password = createUserRequest.password!!,
            userActive = createUserRequest.userActive!!,
            userRole = createUserRequest.userRole!!,
            createdAt = Date(),
            createdBy = createUserRequest.userName!!,
            modifyDate = Date(),
            modifyBy = ""
        )

        userRepository.save(user)

        return convertUserToUserResponse(user)
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
            userActive = updateUserRequest.userActive!!
            userRole = updateUserRequest.userRole!!
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
        val users: List<User> = page.get().collect(Collectors.toList())
        return users.map { convertUserToUserResponse(it) }
    }

    private fun findByIdOrThrowNotFound(userId: String): User {
        val user = userRepository.findByIdOrNull(userId)
        if (user == null){
            throw NotFoundException()
        } else {
            return user
        }
    }

    private fun convertUserToUserResponse(user: User): UserResponse{
        return UserResponse(
            userId = user.userId,
            userName = user.userName,
            email = user.email,
            password = user.password,
            userActive = user.userActive,
            userRole = user.userRole,
            createdAt = user.createdAt,
            createdBy = user.userName,
            modifyDate = user.modifyDate,
            modifyBy = ""
        )
    }
}