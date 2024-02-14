package com.example.api.domain.service.impl

import com.example.api.domain.api.ProductApi
import com.example.api.domain.api.ProductApiFixture
import com.example.api.domain.exception.NotValidException
import com.example.api.domain.model.Product
import com.example.api.domain.model.ProductFixture
import com.example.api.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.Optional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DefaultProductServiceTest {

    private val productRepository: ProductRepository = mockk();
    private val defaultProductService: DefaultProductService = DefaultProductService(productRepository);


    @Test
    fun givenASingleProduct_whenGetProducts_thenReturnAllProducts() {
        every { productRepository.findAll() } returns ProductFixture.mockedSingleElementList();
        val productApiList = defaultProductService.getAll()
        verify(exactly = 1) { productRepository.findAll() }
        assertEquals( ProductApiFixture.mockedSingleElementList(), productApiList)
    }

    @Test
    fun givenMoreThanOneProduct_whenGetProducts_thenReturnAllProducts() {
        every { productRepository.findAll() } returns ProductFixture.mockedList();
        val productApiList = defaultProductService.getAll()
        verify(exactly = 1) { productRepository.findAll() }
        assertEquals( ProductApiFixture.mockedList(), productApiList)
    }

    @Test
    fun givenANonExistentProduct_whenAddProduct_thenSaveTheNewProductAndReturn() {
        val productApi: ProductApi = ProductApi("A", 1, BigDecimal(3.6))
        val product: Product = Product("A", 1, BigDecimal(3.6))
        every { productRepository.findById("A") } returns Optional.empty();
        every { productRepository.save(any()) } returns product;
        val newProductApi = defaultProductService.add(productApi)
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 1) { productRepository.save(any()) }
        assertEquals( productApi, newProductApi)
    }

    @Test
    fun givenAnExistentProduct_whenAddProduct_thenThrowNotValidException() {
        val productApi: ProductApi = ProductApi("A", 1, BigDecimal(3.6))
        val product: Product = Product("A", 1, BigDecimal(3.6))
        every { productRepository.findById("A") } returns Optional.of(product);

        assertThrows<NotValidException> { defaultProductService.add(productApi) }
        verify(exactly = 1) { productRepository.findById("A") }
        verify(exactly = 0) { productRepository.save(any()) }
    }

}