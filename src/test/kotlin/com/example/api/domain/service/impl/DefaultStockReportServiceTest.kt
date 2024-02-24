package com.example.api.domain.service.impl

import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DefaultStockReportServiceTest {

    private val productRepository: ProductRepository = mockk();
    private val defaultStockReportService: DefaultStockReportService = DefaultStockReportService(productRepository);

    @Test
    fun givenExistentProducts_whenGenerateReport_thenReturnReportAsString() {
        val product1: Product = Product("B", 100, BigDecimal(3.6))
        val product2: Product = Product("F", 80, BigDecimal(3.6))
        val product3: Product = Product("C", 7, BigDecimal(3.6))
        val product4: Product = Product("D", 7, BigDecimal(3.6))
        val product5: Product = Product("A", 70, BigDecimal(3.6))
        val product6: Product = Product("E", 70, BigDecimal(3.6))

        val productsList : List<Product> = listOf(product1, product2, product3, product4, product5, product6)

        val expectedReport = "All products in stock in alphabetical order\n" +
                "Product name;Stock\n" +
                "A;70\n" +
                "B;100\n" +
                "C;7\n" +
                "D;7\n" +
                "E;70\n" +
                "F;80\n" +
                "Top three products with most stock\n" +
                "Product name;Stock\n" +
                "B;100\n" +
                "F;80\n" +
                "A;70\n"

        every { productRepository.findAll() } returns productsList;

        val report : String = defaultStockReportService.generateReport()
        assertNotNull(report)
        assertEquals(expectedReport, report)
        verify(exactly = 1) { productRepository.findAll() }
    }


    @Test
    fun givenExistentProductsAndOnlyTwoProducts_whenGenerateReport_thenReturnReportAsString() {
        val product1: Product = Product("B", 100, BigDecimal(3.6))
        val product2: Product = Product("F", 80, BigDecimal(3.6))

        val productsList : List<Product> = listOf(product1, product2)

        val expectedReport = "All products in stock in alphabetical order\n" +
                "Product name;Stock\n" +
                "B;100\n" +
                "F;80\n" +
                "Top three products with most stock\n" +
                "Product name;Stock\n" +
                "B;100\n" +
                "F;80\n"

        every { productRepository.findAll() } returns productsList;

        val report : String = defaultStockReportService.generateReport()
        assertNotNull(report)
        assertEquals(expectedReport, report)
        verify(exactly = 1) { productRepository.findAll() }
    }

    @Test
    fun givenNoProducts_whenGenerateReport_thenReturnEmptyReportAsString() {

        val productsList : List<Product> = ArrayList<Product>()

        val expectedReport = "All products in stock in alphabetical order\n" +
                "Product name;Stock\n" +
                "Top three products with most stock\n" +
                "Product name;Stock\n"

        every { productRepository.findAll() } returns productsList;

        val report : String = defaultStockReportService.generateReport()
        assertNotNull(report)
        assertEquals(expectedReport, report)
        verify(exactly = 1) { productRepository.findAll() }
    }

}