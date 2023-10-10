package com.project.saluyustore.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.project.saluyustore.model.request.LoginUserRequest
import com.project.saluyustore.model.response.WebResponse
import com.project.saluyustore.repository.MasterUserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.jvm.Throws

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTet {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: MasterUserRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp(){
        userRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun loginFailed(){
        val request = LoginUserRequest(
            username = "test",
            password = "test"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/saluyustore-service/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        ).andDo { result ->
            val response = objectMapper.readValue(result.response.contentAsString, object : TypeReference<WebResponse<String>>(){})
            assertNotNull(response.errors)
        }
    }
}