package com.project.saluyustore.service.masterusers

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.model.response.UserResponse
import jakarta.servlet.http.HttpServletRequest

interface MasterUsersService {

    fun create(createUserRequest: CreateUserRequest): UserResponse

    fun get(userId: String): UserResponse

    fun update(userId: String, updateUserRequest: UpdateUserRequest): UserResponse

    fun delete(userId: String)

    fun list(listUserRequest: ListUserRequest): List<UserResponse>

    fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse
}