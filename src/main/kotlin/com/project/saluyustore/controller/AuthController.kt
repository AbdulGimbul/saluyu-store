package com.project.saluyustore.controller

import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.service.masterusers.AuthService
import com.project.saluyustore.util.HttpResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthServiceController(val authService: AuthService) {

    @PostMapping("/login")
    fun loginUser(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpServletRequest: HttpServletRequest,
    ): ResponseEntity<*> {
        return try {
            val loginResponse = authService.login(loginUserRequest, httpServletRequest)

            HttpResponse.setResp(loginResponse, "Login Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }
}