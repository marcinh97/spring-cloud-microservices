package com.horakm.sample.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController {

    @Value("\${property1}")
    lateinit var prop1: String

    @GetMapping
    fun getProperty(): String = prop1

}