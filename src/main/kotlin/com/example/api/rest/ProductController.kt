package com.example.api.rest

import com.example.api.domain.service.ProductService
import com.example.api.domain.api.ProductApi
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(
        private val productService: ProductService
) {

    @GetMapping("")
    fun getProducts(): List<ProductApi> {
        return productService.getAll()
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@Valid @RequestBody productApi: ProductApi): ProductApi {
        return productService.add(productApi)
    }
}