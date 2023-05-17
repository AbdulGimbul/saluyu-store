package com.project.saluyustore.util

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.Serializable
import java.util.*

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class HttpResponse<T> : Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private var data: T? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private var message: String? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private var totalRow: Int? = null
    private var timestamp: Date? = null
    private var status: Int? = null

    companion object {
        private const val serialVersionUID = 8987326349450405819L
        fun <T> response(
            data: T,
            message: String?,
            total: Int?,
            status: HttpStatus
        ): ResponseEntity<HttpResponse<Any>> {
            val httpResponse = HttpResponse<Any>()
            httpResponse.data = data
            httpResponse.message = message
            httpResponse.totalRow = total
            httpResponse.timestamp = Date()
            httpResponse.status = status.value()

            return ResponseEntity.status(status.value()).body(httpResponse)
        }

        fun <T> response(data: T, total: Int?, status: HttpStatus): ResponseEntity<HttpResponse<Any>> {
            val httpResponse = HttpResponse<Any>()
            httpResponse.data = data
            httpResponse.totalRow = total
            httpResponse.timestamp = Date()
            httpResponse.status = status.value()

            return ResponseEntity.status(status.value()).body(httpResponse)
        }

        fun <T> response(data: T, message: String?, status: HttpStatus): ResponseEntity<HttpResponse<Any>> {
            val httpResponse = HttpResponse<Any>()
            httpResponse.apply {
                this.data = data
                this.message = message
                this.status = status.value()
            }

//            with(httpResponse){
//                this.data = data
//                this.message = message
//                this.status = status.value()
//            }

            return ResponseEntity.status(status.value()).body(httpResponse)
        }

        fun <T> response(data: T, status: HttpStatus): ResponseEntity<HttpResponse<Any>> {
            return ResponseEntity.status(status.value()).body<HttpResponse<Any>>(
                HttpResponse.builder<Any>()
                    .data(data)
                    .timestamp(Date())
                    .status(status.value())
                    .build()
            )
        }

        fun <T> response(message: String?, status: HttpStatus): ResponseEntity<HttpResponse<Any>> {
            return ResponseEntity.status(status.value()).body<HttpResponse<Any>>(
                HttpResponse.builder<Any>()
                    .message(message)
                    .timestamp(Date())
                    .status(status.value())
                    .build()
            )
        }
    }
}
