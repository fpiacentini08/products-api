package com.example.api.domain.service.impl

import com.example.api.domain.api.StockApi
import com.example.api.domain.exception.NotValidException
import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import com.example.api.domain.service.StockService
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultStockService : StockService {

    private val productRepository: ProductRepository

    @Autowired
    constructor(productRepository: ProductRepository) {
        this.productRepository = productRepository

    }

    override fun add(stockApi: StockApi): StockApi {
        val name = stockApi.name
        val product: Optional<Product> = productRepository.findById(name)
        if(product.isEmpty){
            throw NotValidException("ERR03", "Product does not exist")
        }
        var storedProduct : Product = product.get()
        val quantity = storedProduct.quantity + stockApi.quantity
        productRepository.save(Product(name, quantity, storedProduct.price))
        return StockApi(name, quantity)

    }

    override fun substract(stockApi: StockApi): StockApi {
        val name = stockApi.name
        val product: Optional<Product> = productRepository.findById(name)
        if(product.isEmpty){
            throw NotValidException("ERR03", "Product does not exist")
        }
        var storedProduct : Product = product.get()
        if(storedProduct.quantity < stockApi.quantity){
            throw NotValidException("ERR04", "Stock is not enough to substract from")
        }
        val quantity = storedProduct.quantity - stockApi.quantity
        productRepository.save(Product(name, quantity, storedProduct.price))
        return StockApi(name, quantity)
    }
}