package com.project.saluyustore.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class HttpResponse {
    companion object {
        fun <T> setResp(data: T, message: String, total: Int, status: HttpStatus): ResponseEntity<*> {
            val httpResp = buildMap {
                put("data", data)
                put("message", message)
                put("totalRow", total)
                put("timestamp", Date())
                put("status", status.value())
            }
            return ResponseEntity.status(status.value()).body(httpResp)
        }

        fun <T> setResp(data: T, total: Int, status: HttpStatus): ResponseEntity<*> {
            val httpResp = buildMap {
                put("data", data)
                put("totalRow", total)
                put("timestamp", Date())
                put("status", status.value())
            }
            return ResponseEntity.status(status.value()).body(httpResp)
        }

        fun <T> setResp(data: T, message: String, status: HttpStatus): ResponseEntity<*> {
            val httpResp = buildMap {
                put("data", data)
                put("message", message)
                put("timestamp", Date())
                put("status", status.value())
            }
            return ResponseEntity.status(status.value()).body(httpResp)
        }

        fun <T> setResp(data: T, status: HttpStatus): ResponseEntity<*> {
            val httpResp = buildMap {
                put("data", data)
                put("timestamp", Date())
                put("status", status.value())
            }
            return ResponseEntity.status(status.value()).body(httpResp)
        }

        fun <T> setResp(message: String, status: HttpStatus): ResponseEntity<*> {
            val httpResp = buildMap {
                put("message", message)
                put("timestamp", Date())
                put("status", status.value())
            }
            return ResponseEntity.status(status.value()).body(httpResp)
        }
    }
}