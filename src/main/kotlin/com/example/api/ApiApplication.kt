package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("application")
@SpringBootApplication
open class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
