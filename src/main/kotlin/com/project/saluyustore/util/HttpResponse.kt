package com.project.saluyustore.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class HttpResponse {
    companion object {
        fun <T> setResp(data: T? = null, message: String? = null, total: Int? = null, status: HttpStatus): ResponseEntity<*> {
            val httpResp = mutableMapOf<String, Any?>()
            httpResp["timestamp"] = Date()
            httpResp["status"] = status.value()

            if (data != null) {
                httpResp["data"] = data
            }
            if (message != null) {
                httpResp["message"] = message
            }
            if (total != null) {
                httpResp["totalRow"] = total
            }

            return ResponseEntity.status(status.value()).body(httpResp)
        }
    }
}
