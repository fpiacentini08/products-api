package com.example.api.rest

import com.example.api.domain.api.ProductApi
import com.example.api.domain.api.ProductApiFixture
import com.example.api.domain.exception.NotValidException
import com.example.api.domain.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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

    @Test
    fun givenAProduct_whenAddProduct_thenReturnProduct() {
        val productApi : ProductApi = ProductApiFixture.mockedSingleElementList().get(0)
        every { productService.add(any()) } returns productApi;

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"A\",\"quantity\": 1,\"price\": 2.0}"))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.price").value(BigDecimal(2.0)))

    }

    @Test
    fun givenAProduct_whenAddProductThatAlreadyExists_thenReturnBadRequest() {
        every { productService.add(any()) } throws NotValidException("ERR01", "Product name already exists");

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"A\",\"quantity\": 1,\"price\": 2.0}"))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ERR01"))
                .andExpect(jsonPath("$.message").value("Product name already exists"))

    }

    @Test
    fun givenAProductWithNoName_whenAddProduct_thenReturnBadRequest() {
        every { productService.add(any()) } throws NotValidException("ERR01", "Product name already exists");

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\": 1,\"price\": 2.0}"))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ERR02"))
                .andExpect(jsonPath("$.message").value("Bad request"))

    }

    @Test
    fun givenAProductWithNoPrice_whenAddProduct_thenReturnBadRequest() {
        every { productService.add(any()) } throws NotValidException("ERR01", "Product name already exists");

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quantity\": 1,\"name\": \"A\"}"))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ERR02"))
                .andExpect(jsonPath("$.message").value("Bad request"))

    }

    @Test
    fun givenAProductWithNoQuantity_whenAddProduct_thenReturnProduct() {
        val productApi : ProductApi = ProductApiFixture.mockedSingleElementList().get(0)
        every { productService.add(any()) } returns productApi;

        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"A\",\"price\": 2.0}"))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("A"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.price").value(BigDecimal(2.0)))

    }

}