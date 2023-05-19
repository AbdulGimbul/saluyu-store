package com.project.saluyustore.service.user

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse

interface UserService {

    fun create(createUserRequest: CreateUserRequest): UserResponse

    fun get(userId: String): UserResponse

    fun update(userId: String, updateUserRequest: UpdateUserRequest): UserResponse

    fun delete(userId: String)

    fun list(listUserRequest: ListUserRequest): List<UserResponse>
}