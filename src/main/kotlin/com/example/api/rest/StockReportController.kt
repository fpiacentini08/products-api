package com.example.api.rest

import com.example.api.domain.api.StockApi
import com.example.api.domain.service.StockReportService
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
@RequestMapping("/report")
class StockReportController @Autowired constructor(
    private val stockReportService: StockReportService
) {

    @PostMapping("/stock")
    @ResponseStatus(HttpStatus.CREATED)
    fun generateStockReport(): String {
        return stockReportService.generateReport()
    }



}