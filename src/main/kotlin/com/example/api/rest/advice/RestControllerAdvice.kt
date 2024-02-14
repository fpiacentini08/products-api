package com.example.api.rest.advice

import com.example.api.domain.exception.NotValidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestControllerAdvice {

    @ExceptionHandler
    fun handleNotValidException(ex: NotValidException): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel(ex.code ,ex.message)
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel("ERR02" ,"Bad request")
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }


}
