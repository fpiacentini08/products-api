package com.example.api.domain.service.impl

import com.example.api.domain.api.ProductApiFixture
import com.example.api.domain.model.ProductFixture
import com.example.api.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DefaultProductServiceTest {

    val productRepository: ProductRepository = mockk();
    val defaultProductService: DefaultProductService = DefaultProductService(productRepository);


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

}