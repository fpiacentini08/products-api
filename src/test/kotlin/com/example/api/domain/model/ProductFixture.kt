package com.example.api.domain.model

import java.math.BigDecimal

class ProductFixture{
    companion object {
        fun mockedSingleElementList() = listOf(Product("A", 1, BigDecimal(2.0)))
        fun mockedList() = listOf(Product("A", 1, BigDecimal(2.0)),
                Product("B", 7, BigDecimal(3.15)))
    }


}