package com.project.saluyustore.controller

import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.service.masteruser.AuthService
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
class AuthController(private val authService: AuthService) {

    @PostMapping(
        path = ["/login"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun loginUser(
        @RequestBody loginUserRequest: LoginUserRequest,
        httpServletRequest: HttpServletRequest,
    ): ResponseEntity<*> {
        val userLogin = authService.login(loginUserRequest, httpServletRequest)
        return HttpResponse.setResp(data = userLogin, message = "Success", status = HttpStatus.OK)
    }
}