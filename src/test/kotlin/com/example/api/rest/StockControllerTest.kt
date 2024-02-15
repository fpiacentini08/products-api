package com.example.api.rest

import com.example.api.domain.api.StockApi
import com.example.api.domain.service.StockService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [StockController::class])
class StockControllerTest (@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var stockService: StockService

    @Test
    fun givenAProduct_whenAddStock_thenReturnNewStock() {
        val stockApi: StockApi = StockApi("A", 7)
        every { stockService.add(any()) } returns stockApi;

        mockMvc.perform(
            MockMvcRequestBuilders.post("/stock/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"A\",\"quantity\": 1}"))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("A"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(7))

    }

}