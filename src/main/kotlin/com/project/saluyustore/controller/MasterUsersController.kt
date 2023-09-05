package com.project.saluyustore.controller

import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.ListUserRequest
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.service.masterusers.MasterUsersService
import com.project.saluyustore.util.HttpResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class MasterUsersController(val masterUsersService: MasterUsersService) {

    @PostMapping
    fun createUser(@RequestBody body: CreateUserRequest): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.create(body)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.get(userId)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable("userId") userId: Long,
                   @RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<*> {
        return try {
            val userResponse = masterUsersService.update(userId, updateUserRequest)

            HttpResponse.setResp(userResponse, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable("userId") userId: Long): ResponseEntity<*> {
        return try {
            masterUsersService.delete(userId)

            HttpResponse.setResp("Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listUsers(@RequestParam(value = "size", defaultValue = "10") size: Int,
                  @RequestParam(value = "page", defaultValue = "0") page: Int): ResponseEntity<*> {
        return try {
            val request = ListUserRequest(page = page, size = size)
            val responses = masterUsersService.list(request)

            HttpResponse.setResp(responses, "Success", HttpStatus.OK)
        } catch (e: Exception) {
            HttpResponse.setResp(null, e.message, HttpStatus.BAD_REQUEST)
        }
    }
}