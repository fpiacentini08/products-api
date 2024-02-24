package com.example.api.rest

import com.example.api.domain.api.StockApi
import com.example.api.domain.service.StockService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stock")
class StockController @Autowired constructor(
    private val stockService: StockService
) {

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToStock(@Valid @RequestBody stockApi: StockApi): StockApi {
        return stockService.add(stockApi)
    }

    @PostMapping("/substract")
    @ResponseStatus(HttpStatus.CREATED)
    fun substractFromStock(@Valid @RequestBody stockApi: StockApi): StockApi {
        return stockService.substract(stockApi)
    }

}