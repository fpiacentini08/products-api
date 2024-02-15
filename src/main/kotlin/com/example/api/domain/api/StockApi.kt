package com.example.api.domain.api

import jakarta.validation.constraints.NotBlank

data class StockApi(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
    val quantity: Int = 0
){

}
