package com.horakm.sample.controller

import com.horakm.sample.client.LangServiceClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(private val langServiceClient: LangServiceClient) {

    @Value("\${property1}")
    lateinit var prop1: String

    @GetMapping
    fun getProperty(): String = prop1

    @GetMapping("/externalProperty")
    fun getClientProperty(): String = langServiceClient.getProperty()

}
