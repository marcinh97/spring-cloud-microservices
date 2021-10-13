package com.horakm.example.controller

import com.horakm.example.domain.Person
import com.horakm.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/example/person")
class PersonController(private val personRepository: PersonRepository) {

    // to showcase that config from config server has been injected
    @Value("\${property1}")
    lateinit var prop1: String

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Person? {
        return personRepository.findById(id)
    }

    @PostMapping
    fun add(@RequestBody person: Person) = personRepository.add(person)

    @GetMapping
    fun findAll(): MutableList<Person> = personRepository.findAll()

    @GetMapping("/property1")
    fun getProperty(): String = prop1
}
