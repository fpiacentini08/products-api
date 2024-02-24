package com.example.api.domain.service.impl

import com.example.api.domain.api.StockApi
import com.example.api.domain.exception.NotValidException
import com.example.api.domain.model.Product
import com.example.api.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DefaultStockServiceTest {

    private val productRepository: ProductRepository = mockk();
    private val defaultStockService: DefaultStockService = DefaultStockService(productRepository);

    @Test
    fun givenAnExistentProduct_whenAddStock_thenAddToStockAndReturnNewStock() {
        val stockApi: StockApi = StockApi("A", 1)
        val product: Product = Product("A", 7, BigDecimal(3.6))
        every { productRepository.findById("A") } returns Optional.of(product);
        every { productRepository.save(Product("A", 7+1, BigDecimal(3.6))) } returns product;

        val stockApiResponse : StockApi = defaultStockService.add(stockApi)
        assertNotNull(stockApiResponse)
        assertEquals("A", stockApiResponse.name)
        assertEquals(7+1, stockApiResponse.quantity)

        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 1) { productRepository.save(Product("A", 7+1, BigDecimal(3.6))) }
    }

    @Test
    fun givenANonExistentProduct_whenAddStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", 1)
        every { productRepository.findById("A") } returns Optional.empty();

        assertThrows<NotValidException> { defaultStockService.add(stockApi) }
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenAnExistentProduct_whenSubstractStock_thenSubstractFromStockAndReturnNewStock() {
        val stockApi: StockApi = StockApi("A", 1)
        val product: Product = Product("A", 7, BigDecimal(3.6))
        every { productRepository.findById("A") } returns Optional.of(product);
        every { productRepository.save(Product("A", 7-1, BigDecimal(3.6))) } returns product;

        val stockApiResponse : StockApi = defaultStockService.substract(stockApi)
        assertNotNull(stockApiResponse)
        assertEquals("A", stockApiResponse.name)
        assertEquals(7-1, stockApiResponse.quantity)
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 1) { productRepository.save(Product("A", 7-1, BigDecimal(3.6))) }
    }

    @Test
    fun givenAnExistentProduct_whenSubstractStockAndStockIsNotEnough_thenThrowException() {
        val stockApi: StockApi = StockApi("A", 8)
        val product: Product = Product("A", 7, BigDecimal(3.6))
        every { productRepository.findById("A") } returns Optional.of(product);

        assertThrows<NotValidException> {  defaultStockService.substract(stockApi) }
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenANonExistentProduct_whenSubstractStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", 1)
        every { productRepository.findById("A") } returns Optional.empty();

        assertThrows<NotValidException> { defaultStockService.substract(stockApi)  }
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenANegativeQuantityInStockApiRequest_whenAddStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", -1)
        assertThrows<NotValidException> {  defaultStockService.add(stockApi) }
        verify(exactly = 0) { productRepository.findById(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenZeroQuantityInStockApiRequest_whenAddStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", 0)
        assertThrows<NotValidException> {  defaultStockService.add(stockApi) }
        verify(exactly = 0) { productRepository.findById(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenANegativeQuantityInStockApiRequest_whenSubstractStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", -1)
        assertThrows<NotValidException> {  defaultStockService.substract(stockApi) }
        verify(exactly = 0) { productRepository.findById(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

    @Test
    fun givenZeroQuantityInStockApiRequest_whenSubstractStock_thenThrowException() {
        val stockApi: StockApi = StockApi("A", 0)
        assertThrows<NotValidException> {  defaultStockService.add(stockApi) }
        verify(exactly = 0) { productRepository.findById(any()) }
        verify(exactly = 0) { productRepository.save(any()) }
    }

}