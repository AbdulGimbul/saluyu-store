package com.project.saluyustore.service.masterusers

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse

interface MasterUsersService {

    fun create(createUserRequest: CreateUserRequest): UserResponse

    fun get(userId: Long): UserResponse

    fun update(userId: Long, updateUserRequest: UpdateUserRequest): UserResponse

    fun delete(userId: Long)

    fun list(listUserRequest: ListUserRequest): List<UserResponse>
}