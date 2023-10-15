package com.project.saluyustore.controller

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.AuthResponse
import com.project.saluyustore.model.response.UserLoginResponse
import com.project.saluyustore.service.masterusers.AuthService
import com.project.saluyustore.util.HttpResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.security.sasl.AuthenticationException

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