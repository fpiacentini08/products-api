package com.example.api.domain.service

import com.example.api.domain.api.ProductApi

interface ProductService {
    fun getAll() : List<ProductApi>
}