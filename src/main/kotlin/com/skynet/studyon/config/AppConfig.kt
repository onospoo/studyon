package com.skynet.studyon.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.web.client.RestTemplate


@Configuration
//@EnableMongoRepositories
class AppConfig {

    @Bean
    fun getRestTemplate(): RestTemplate =
            RestTemplate()

    @Bean
    fun getMapper(): ObjectMapper =
            ObjectMapper().registerModule(KotlinModule())
}