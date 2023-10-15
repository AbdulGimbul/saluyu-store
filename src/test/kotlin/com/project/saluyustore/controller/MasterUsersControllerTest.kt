package com.project.saluyustore.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.entity.Role
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.repository.MasterUserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class MasterUsersControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var masterUserRepository: MasterUserRepository

//    @BeforeEach
//    fun setUp(){
//
//    }

    @Test
    fun registerSuccess(){
        val user = masterUserRepository.findFirstByUserName("abdl").orElse(null)
        if (user != null) {
            masterUserRepository.delete(user)
        }

        val request = CreateUserRequest(
            username = "abdl",
            email = "abdl@example.com",
            password = "rahasia",
            userActive = true,
            userRole = Role.BUYER
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }

    @Test
    fun registerBadRequest(){
        val request = CreateUserRequest(
            username = "",
            email = "",
            password = "",
            userActive = false,
            userRole = Role.BUYER
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("username: Username must not be blank, email: Email must not be blank, password: Password must not be blank"))
    }

    @Test
    fun registerDuplicate(){
        val user = masterUserRepository.findFirstByEmail("duplicate@example.com").orElse(null)
        if (user != null) {
            masterUserRepository.delete(user)
        }

        val masterUsers = MasterUsers(
            userName = "duplicate",
            email = "duplicate@example.com",
            userRole = Role.BUYER,
            passwd = BCrypt.hashpw("duplicate", BCrypt.gensalt()),
            createdAt = Date(),
            createdBy = "duplicate",
            modifiedAt = Date(),
            modifiedBy = "duplicate",
            userActive = true,
            token = "duplicate",
            tokenExpiredAt = System.currentTimeMillis() + 100000
        )

        masterUserRepository.save(masterUsers)

        val request = CreateUserRequest(
            username = "email duplicate",
            email = "duplicate@example.com",
            password = "duplicate",
            userActive = true,
            userRole = Role.BUYER
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email already registered"))
    }
}