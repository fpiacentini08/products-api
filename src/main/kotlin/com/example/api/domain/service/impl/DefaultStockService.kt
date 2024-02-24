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
        validateReceivedStockIsPositive(stockApi)
        val name = stockApi.name
        val product: Optional<Product> = productRepository.findById(name)
        validateProductIsPresent(product)
        var storedProduct : Product = product.get()
        val quantity = storedProduct.quantity + stockApi.quantity
        productRepository.save(Product(name, quantity, storedProduct.price))
        return StockApi(name, quantity)

    }


    override fun substract(stockApi: StockApi): StockApi {
        validateReceivedStockIsPositive(stockApi)
        val name = stockApi.name
        val product: Optional<Product> = productRepository.findById(name)
        validateProductIsPresent(product)
        val storedProduct : Product = product.get()
        val quantity = storedProduct.quantity - stockApi.quantity
        validateCalculatedQuantityIsNotNegative(quantity)
        productRepository.save(Product(name, quantity, storedProduct.price))
        return StockApi(name, quantity)
    }

    private fun validateProductIsPresent(product: Optional<Product>) {
        if (product.isEmpty) {
            throw NotValidException("ERR03", "Product does not exist")
        }
    }

    private fun validateCalculatedQuantityIsNotNegative(quantity: Int) {
        if (quantity < 0) {
            throw NotValidException("ERR04", "Stock is not enough to substract from")
        }
    }

    private fun validateReceivedStockIsPositive(stockApi: StockApi) {
        if (stockApi.quantity < 1){
            throw NotValidException("ERR05", "Requested stock quantity should be a positive number.")
        }
    }

}