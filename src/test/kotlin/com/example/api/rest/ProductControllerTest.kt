package com.example.api.rest

import com.example.api.domain.api.ProductApiFixture
import com.example.api.domain.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal


@WebMvcTest
class ProductControllerTest (@Autowired val mockMvc: MockMvc){

    @MockkBean
    lateinit var productService: ProductService

    @Test
    fun givenASingleProduct_whenGetProducts_thenReturnAllProducts() {
        every { productService.getAll() } returns ProductApiFixture.mockedSingleElementList();

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].price").value(BigDecimal(2.0)))

    }

    @Test
    fun givenMoreThanOneProduct_whenGetProducts_thenReturnAllProducts() {
        every { productService.getAll() } returns ProductApiFixture.mockedList();

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].price").value(BigDecimal(2.0)))
                .andExpect(jsonPath("$[1].name").value("B"))
                .andExpect(jsonPath("$[1].quantity").value(7))
                .andExpect(jsonPath("$[1].price").value(BigDecimal(3.15)))

    }

}