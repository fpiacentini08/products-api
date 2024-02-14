package com.example.api.domain.service.impl

import com.example.api.domain.api.ProductApi
import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import com.example.api.domain.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultProductService : ProductService {

    var productRepository: ProductRepository

    @Autowired
    constructor(productRepository: ProductRepository) {
        this.productRepository = productRepository

    }

    override fun getAll(): List<ProductApi> {
        val products = productRepository.findAll()
        val productApis = ArrayList<ProductApi>()
        for(product:Product in products){
            productApis.add(product.toProductApi())
        }
        return productApis

    }
}