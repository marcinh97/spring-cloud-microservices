package com.horakm.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SampleServiceApplication

fun main(args: Array<String>) {
    runApplication<SampleServiceApplication>(*args)
}
