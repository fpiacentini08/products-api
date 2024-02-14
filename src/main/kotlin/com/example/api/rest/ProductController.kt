package com.example.api.rest

import com.example.api.domain.service.ProductService
import com.example.api.domain.api.ProductApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(
        val productService: ProductService
) {

    @GetMapping("")
    fun getProduct(): List<ProductApi> {
        return productService.getAll()
    }
}