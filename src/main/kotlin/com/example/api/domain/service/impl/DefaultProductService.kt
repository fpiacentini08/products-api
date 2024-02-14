package com.example.api.domain.service.impl

import com.example.api.domain.api.ProductApi
import com.example.api.domain.exception.NotValidException
import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import com.example.api.domain.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Service
class DefaultProductService : ProductService {

    private val productRepository: ProductRepository

    @Autowired
    constructor(productRepository: ProductRepository) {
        this.productRepository = productRepository

    }

    override fun getAll(): List<ProductApi> {
        val products = productRepository.findAll()
        return products.parallelStream().map { p -> p.toProductApi() }.collect(Collectors.toList())

    }

    override fun add(productApi: ProductApi): ProductApi {
        val product: Optional<Product> = productRepository.findById(productApi.name)
        if(product.isPresent){
            throw NotValidException("ERR01", "Product name already exists")
        }
        val newProduct : Product = productRepository.save(Product(productApi.name, productApi.quantity, productApi.price))
        return newProduct.toProductApi()
    }
}