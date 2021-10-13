package com.horakm.sample.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "example-service", path="/example/person")
interface ExampleServiceClient {
    @GetMapping("/property1")
    fun getProperty(): String
}
