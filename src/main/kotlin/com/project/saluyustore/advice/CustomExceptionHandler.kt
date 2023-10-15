package com.project.saluyustore.advice

import com.project.saluyustore.util.HttpResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.SignatureException

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleSecurityException(e: Exception): ResponseEntity<*> {

        if (e is BadCredentialsException) {
            return HttpResponse.setResp<String>(message = "Username or password is wrong!", status = HttpStatus.UNAUTHORIZED)
        }

        if (e is AuthenticationException) {
            return HttpResponse.setResp<String>(message = "No auth header provider found", status = HttpStatus.UNAUTHORIZED)
        }

        if (e is AccessDeniedException) {
            return HttpResponse.setResp<String>(message = "Not authorized", status = HttpStatus.FORBIDDEN)
        }

        if (e is SignatureException) {
            return HttpResponse.setResp<String>(message = "JWT signature is not valid!", status = HttpStatus.FORBIDDEN)
        }

        if (e is ExpiredJwtException) {
            return HttpResponse.setResp<String>(message = "JWT token already expired", status = HttpStatus.FORBIDDEN)
        }

        // Add a default response for other exceptions
        return HttpResponse.setResp<String>(message = "An error occurred", status = HttpStatus.INTERNAL_SERVER_ERROR)
    }

}