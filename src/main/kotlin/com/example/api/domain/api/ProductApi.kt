package com.example.api.domain.api

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductApi(
        @field:NotBlank(message = "Name must not be blank")
        val name: String,
        val quantity: Int = 0,
        @field:NotNull(message = "Price must not be null")
        val price: BigDecimal){

}
