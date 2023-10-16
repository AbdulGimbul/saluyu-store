package com.project.saluyustore.service.masterusers

import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.UserLoginResponse
import jakarta.servlet.http.HttpServletRequest

interface AuthService {
    fun login(loginUserRequest: LoginUserRequest, httpServletRequest: HttpServletRequest): UserLoginResponse
}