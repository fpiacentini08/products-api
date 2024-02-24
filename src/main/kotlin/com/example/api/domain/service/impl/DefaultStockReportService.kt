package com.example.api.domain.service.impl

import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import com.example.api.domain.service.StockReportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultStockReportService : StockReportService {

    private val productRepository: ProductRepository

    @Autowired
    constructor(productRepository: ProductRepository) {
        this.productRepository = productRepository

    }


    override fun generateReport(): String {
        val reportStringBuilder = StringBuilder()

        val products : List<Product> = productRepository.findAll()

        val productsSortedByName = products.sortedBy { product: Product -> product.name }
        reportStringBuilder.append("All products in stock in alphabetical order\n")
        reportStringBuilder.append("Product name;Stock\n")
        for(product in productsSortedByName){
            val name = product.name
            val stock = product.quantity
            reportStringBuilder.append("$name;$stock\n")
        }
        val productsSortedQuantity = products.sortedByDescending { product: Product -> product.quantity }
        reportStringBuilder.append("Top three products with most stock\n")
        reportStringBuilder.append("Product name;Stock\n")
        for(i in 0..2){
            if(i >= productsSortedQuantity.size){
                break
            }
            val name = productsSortedQuantity[i].name
            val stock = productsSortedQuantity[i].quantity
            reportStringBuilder.append("$name;$stock\n")
        }
        return reportStringBuilder.toString()
    }
}