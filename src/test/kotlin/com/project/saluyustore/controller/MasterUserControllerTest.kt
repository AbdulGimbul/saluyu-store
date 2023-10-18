package com.project.saluyustore.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.project.saluyustore.entity.MasterUser
import com.project.saluyustore.entity.MasterUserDetail
import com.project.saluyustore.entity.Role
import com.project.saluyustore.model.request.CreateUserRequest
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.request.UpdateUserRequest
import com.project.saluyustore.repository.MasterUserDetailRepository
import com.project.saluyustore.repository.MasterUserRepository
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MasterUserControllerTest {

    private val loggerFactory = LoggerFactory.getLogger(MasterUserControllerTest::class.java)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var masterUserRepository: MasterUserRepository

    @Autowired
    private lateinit var masterUserDetailRepository: MasterUserDetailRepository

    private lateinit var userToken: String

    private var idUserRegis: String = ""

    @BeforeEach
    fun setUp() {
        val request = LoginUserRequest(
            username = "abdl",
            password = "rahasia"
        )

        val tokenResponse = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(
                MockMvcResultMatchers.status().isOk
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.userToken").isNotEmpty)
            .andReturn()
            .response
            .contentAsString

        val jsonNode = objectMapper.readTree(tokenResponse)

        userToken = jsonNode.path("data").path("userToken").asText()

    }

    @Test
    @Order(1)
    fun registerSuccess() {
        val user = masterUserRepository.findFirstByUserName("regis").orElse(null)
        if (user != null) {
            val userDetail = masterUserDetailRepository.findById(user.userId).orElse(null)
            masterUserRepository.delete(user)
            masterUserDetailRepository.delete(userDetail)
        }

        val pswd = RandomStringUtils.randomAlphanumeric(10)

        val request = CreateUserRequest(
            username = "regis",
            email = "regis@example.com",
            password = pswd,
            userActive = true,
            userRole = Role.BUYER
        )

        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
            .andReturn()
            .response
            .contentAsString

        val jsonNode = objectMapper.readTree(response)

        idUserRegis = jsonNode.path("data").path("userId").asText()
    }

    @Test
    fun registerBadRequest() {
        val request = CreateUserRequest(
            username = "",
            email = "bad@example.com",
            password = "rahasia",
            userActive = false,
            userRole = Role.BUYER
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")
                .content(jacksonObjectMapper().writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.message")
                    .value("username: Username must not be blank")
            )
    }

    @Test
    fun registerDuplicate() {
        val user = masterUserRepository.findFirstByEmail("duplicate@example.com").orElse(null)
        if (user != null) {
            val userDetail = masterUserDetailRepository.findById(user.userId).orElse(null)
            masterUserRepository.delete(user)
            masterUserDetailRepository.delete(userDetail)
        }

        val masterUser = MasterUser(
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

        masterUserRepository.save(masterUser)

        val detailUser = MasterUserDetail(userId = masterUser.userId)

        masterUserDetailRepository.save(detailUser)

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
                .header("Authorization", "Bearer $userToken")
                .content(jacksonObjectMapper().writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email already registered"))
    }

    @Test
    fun getUserUnauthorized() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    fun getUserSuccess() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users/552")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")

        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }

    @Test
    fun getAllUserSuccess() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")

        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }

    @Test
    fun getUserTokenNotValid() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOJhYmRsMiIsImlhdCI6MTY5NzQxNTc4MSwiZXhwIjoxNjk3NDE3MjIxfQ.4EV26N453H0yVTrwMFHhI85eVHCBUdkw3sGZKi0nI_U"
                )

        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("JWT signature is not valid!"))
    }

    @Test
    fun getUserTokenExpired() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmRsMiIsImlhdCI6MTY5NzQyMDIyMCwiZXhwIjoxNjk3NDIxNjYwfQ.yoU3vB39hBQeIVLQtzNwm3s5M2cDYfHXqDmVrIxlCEM"
                )

        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("JWT token already expired"))
    }

    @Test
    fun updateRegisterSuccess() {
        val pswd = RandomStringUtils.randomAlphanumeric(10)

        val request = UpdateUserRequest(
            password = pswd,
        )

        loggerFactory.info("cek passwordnya: $pswd")
        loggerFactory.info("cek alah usernya lagi: $idUserRegis")

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/users/$idUserRegis")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }

    @Test
    fun deleteUser() {
        val user = masterUserRepository.findFirstByUserName("delete").orElse(null)
        if (user != null) {
            val userDetail = masterUserDetailRepository.findById(user.userId).orElse(null)
            masterUserRepository.delete(user)
            masterUserDetailRepository.delete(userDetail)
        }

        val masterUser = MasterUser(
            userName = "delete",
            email = "delete@example.com",
            userRole = Role.BUYER,
            passwd = BCrypt.hashpw("delete", BCrypt.gensalt()),
            createdAt = Date(),
            createdBy = "delete",
            modifiedAt = Date(),
            modifiedBy = "delete",
            userActive = true,
            token = "delete",
            tokenExpiredAt = System.currentTimeMillis() + 100000
        )

        val savedUser = masterUserRepository.save(masterUser)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/users/${savedUser.userId}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $userToken")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }
}