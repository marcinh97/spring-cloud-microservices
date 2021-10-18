package com.horakm.gateway.auth.service

import com.horakm.gateway.auth.exception.UserNotAuthorizedException
import com.horakm.gateway.auth.model.User
import com.horakm.gateway.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User as SpringSecurityUser

class UserService(@Autowired private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepository.findByUsername(username)
        if (user == null || user.roles.isEmpty()) {
            throw UserNotAuthorizedException("User not authorized")
        }
        return createSpringUser(user)
    }

    private fun createSpringUser(user: User): SpringSecurityUser {
        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }
        return SpringSecurityUser(user.username, user.password, authorities)
    }
    // https://medium.com/@arjunac009/spring-boot-microservice-with-centralized-authentication-zuul-eureka-jwt-5719e05fde29
}
