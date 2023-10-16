package com.project.saluyustore.controller

import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.service.masterusers.MasterUsersService
import com.project.saluyustore.util.HttpResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "Master User")
class MasterUsersController(val masterUsersService: MasterUsersService) {

    @Operation(
        summary = "Register user",
    )
    @PostMapping
    fun createUser(@RequestBody body: CreateUserRequest): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.create(body)

            HttpResponse.setResp(data = userResponse, message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(
        summary = "Get user",
    )
    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Int): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.get(userId)

            HttpResponse.setResp(data = userResponse, message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(
        summary = "Update user",
    )
    @PutMapping("/{userId}")
    fun updateUser(
        @PathVariable("userId") userId: Int,
        @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.update(userId, updateUserRequest)

            HttpResponse.setResp(userResponse, "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(
        summary = "Delete user",
    )
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable("userId") userId: Int): ResponseEntity<*> {
        return try {
            masterUsersService.delete(userId)

            HttpResponse.setResp<String>(message = "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(
        summary = "Get all user",
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listUsers(
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "page", defaultValue = "0") page: Int
    ): ResponseEntity<*> {
        return try {
            val request = ListUserRequest(page = page, size = size)
            val responses = masterUsersService.list(request)

            HttpResponse.setResp(responses, "Success", status = HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp<String>(message = e.message, status = HttpStatus.BAD_REQUEST)
        }
    }
}