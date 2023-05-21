package com.project.saluyustore.controller

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.service.user.UserService
import com.project.saluyustore.util.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(val userService: UserService) {

    @PostMapping(
            value = ["/api/users"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun createUser(@RequestBody body: CreateUserRequest): ResponseEntity<*> {
        return try {
            val userResponse = userService.create(body)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(
            value = ["/api/users/{userId}"],
            produces = ["application/json"]
    )
    fun getUser(@PathVariable("userId") userId: String): ResponseEntity<*> {
        return try {
            val userResponse = userService.get(userId)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping(
            value = ["/api/users/{userId}"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun updateUser(@PathVariable("userId") userId: String,
                   @RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<*> {
        return try {
            val userResponse = userService.update(userId, updateUserRequest)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping(
            value = ["/api/users/{userId}"],
            produces = ["application/json"]
    )
    fun deleteUser(@PathVariable("userId") userId: String): ResponseEntity<*> {
        return try {
            userService.delete(userId)

            HttpResponse.setResp("Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(
            value = ["/api/users"],
            produces = ["application/json"]
    )
    fun listUsers(@RequestParam(value = "size", defaultValue = "10") size: Int,
                  @RequestParam(value = "page", defaultValue = "0") page: Int): ResponseEntity<*> {
        return try {
            val request = ListUserRequest(page = page, size = size)
            val responses = userService.list(request)

            HttpResponse.setResp(responses, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }
}