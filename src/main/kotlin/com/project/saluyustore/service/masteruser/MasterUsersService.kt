package com.project.saluyustore.service.masteruser

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse

interface MasterUsersService {

    fun create(createUserRequest: CreateUserRequest): UserResponse

    fun get(userId: Int): UserResponse

    fun update(userId: Int, updateUserRequest: UpdateUserRequest): UserResponse

    fun delete(userId: Int)

    fun list(listUserRequest: ListUserRequest): List<UserResponse>
}