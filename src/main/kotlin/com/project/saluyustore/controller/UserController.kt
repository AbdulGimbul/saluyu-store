package com.project.saluyustore.controller

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.model.response.UserResponse
import com.project.saluyustore.model.response.WebResponse
import com.project.saluyustore.service.user.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @PostMapping(
        value = ["/api/users"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun createUser(@RequestBody body: CreateUserRequest): WebResponse<UserResponse>{
        val userResponse = userService.create(body)

        return WebResponse(
            code = 200,
            status = "OK",
            data = userResponse
        )
    }

    @GetMapping(
        value = ["/api/users/{userId}"],
        produces = ["application/json"]
    )
    fun getUser(@PathVariable("userId") userId: String): WebResponse<UserResponse>{
        val userResponse = userService.get(userId)

        return WebResponse(
            code = 200,
            status = "OK",
            data = userResponse
        )
    }

    @PutMapping(
        value = ["/api/users/{userId}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun updateUser(@PathVariable("userId") userId: String,
                   @RequestBody updateUserRequest: UpdateUserRequest): WebResponse<UserResponse>{
        val userResponse = userService.update(userId, updateUserRequest)

        return WebResponse(
            code = 200,
            status = "OK",
            data = userResponse
        )
    }

    @DeleteMapping(
        value = ["/api/users/{userId}"],
        produces = ["application/json"]
    )
    fun deleteUser(@PathVariable("userId") userId: String): WebResponse<String>{
        userService.delete(userId)

        return WebResponse(
            code = 200,
            status = "OK",
            data = userId
        )
    }

    @GetMapping(
        value = ["/api/users"],
        produces = ["application/json"]
    )
    fun listUsers(@RequestParam(value = "size", defaultValue = "10") size: Int,
                  @RequestParam(value = "page", defaultValue = "0") page: Int): WebResponse<List<UserResponse>>{
        val request = ListUserRequest(page = page, size = size)
        val responses = userService.list(request)

        return WebResponse(
            code = 200,
            status = "OK",
            data = responses
        )
    }
}