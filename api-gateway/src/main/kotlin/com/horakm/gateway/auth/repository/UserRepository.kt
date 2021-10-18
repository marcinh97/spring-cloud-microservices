package com.horakm.gateway.auth.repository

import com.horakm.gateway.auth.model.Role
import com.horakm.gateway.auth.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun findByUsername(username: String): User? = User(1, "abc@gmail.com", "admin", "admin", setOf(Role(1, "Admin")))
}
