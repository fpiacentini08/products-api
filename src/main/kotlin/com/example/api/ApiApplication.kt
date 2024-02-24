package com.example.api

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.example.api")
@EnableJpaRepositories("com.example.api.domain.repository")

open class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
