package com.example.api.domain.exception


class NotValidException(code: String, message: String) : RuntimeException(message){
    val code: String = code
}
