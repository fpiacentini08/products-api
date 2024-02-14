package com.example.api.domain.model

import com.example.api.domain.api.ProductApi
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
data class Product(@Id val name: String, val quantity: Int, val price: BigDecimal) {


    fun toProductApi(): ProductApi {
        return ProductApi(name, quantity, price)
    }
}
