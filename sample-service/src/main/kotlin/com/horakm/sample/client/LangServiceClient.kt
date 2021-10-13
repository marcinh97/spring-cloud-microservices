package com.horakm.sample.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "lang-service", path="/person")
interface LangServiceClient {
    @GetMapping("/property1")
    fun getProperty(): String
}
