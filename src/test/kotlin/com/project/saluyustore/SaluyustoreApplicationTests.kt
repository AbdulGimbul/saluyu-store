package com.project.saluyustore

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SaluyustoreApplicationTests {

    @Test
    fun contextLoads() {
        val s = "adjdjkakdkd"
        if (s.matches(".*[A-Z].*".toRegex())) {
            println("true")
        } else {
            println("false")
        }
    }

}
