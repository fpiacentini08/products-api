package com.example.api.domain.service

import com.example.api.domain.api.StockApi

interface StockService {

    fun add(stockApi: StockApi): StockApi
    fun substract(stockApi: StockApi): StockApi
}