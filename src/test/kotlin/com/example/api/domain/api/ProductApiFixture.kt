package com.example.api.domain.api

import java.math.BigDecimal

class ProductApiFixture{
    companion object {
        fun mockedSingleElementList() = listOf(ProductApi("A", 1, BigDecimal(2.0)))
        fun mockedList() = listOf(ProductApi("A", 1, BigDecimal(2.0)),
                ProductApi("B", 7, BigDecimal(3.15)))
    }
}