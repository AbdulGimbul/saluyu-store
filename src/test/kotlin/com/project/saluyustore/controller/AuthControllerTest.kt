package com.project.saluyustore.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.project.saluyustore.entity.MasterUsers
import com.project.saluyustore.entity.Role
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.repository.MasterUserRepository
import org.junit.jupiter.api.Assertions
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
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var masterUserRepository: MasterUserRepository

//    @BeforeEach
//    fun setUp(){
//        given(userRepository.findByUsername("test")).willReturn(null)
//    }

    @Test
    fun loginFailed() {

        val request = LoginUserRequest(
            username = "test",
            password = "test"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(
                MockMvcResultMatchers.status().isUnauthorized
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username or password is wrong!"))
    }

    @Test
    fun loginSuccess(){
        val request = LoginUserRequest(
            username = "abdl",
            password = "rahasia"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(
                MockMvcResultMatchers.status().isOk
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"))
    }

    @Test
    fun logout() {
        val user = masterUserRepository.findFirstByToken("testlogout").orElse(null)
        if (user != null) {
            masterUserRepository.delete(user)
        }

        val masterUsers = MasterUsers(
            userName = "testlogout",
            email = "testlogout@example.com",
            userRole = Role.BUYER,
            passwd = BCrypt.hashpw("testlogout", BCrypt.gensalt()),
            createdAt = Date(),
            createdBy = "testlogout",
            modifiedAt = Date(),
            modifiedBy = "testlogout",
            userActive = true,
            token = "testlogout",
            tokenExpiredAt = System.currentTimeMillis() + 100000
        )

        masterUserRepository.save(masterUsers)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/auth/logout")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer testlogout")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        )
    }
}