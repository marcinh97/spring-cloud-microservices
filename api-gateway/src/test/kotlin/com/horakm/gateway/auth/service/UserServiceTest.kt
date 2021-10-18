package com.horakm.gateway.auth.service

import com.horakm.gateway.auth.exception.UserNotAuthorizedException
import com.horakm.gateway.auth.model.Role
import com.horakm.gateway.auth.model.User
import com.horakm.gateway.auth.repository.UserRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import kotlin.test.assertFailsWith

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class UserServiceTest {
    // https://www.fabrizioduroni.it/2021/01/27/kotlin-junit5-mockk/
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var userService: UserService

    companion object {
        const val ADMIN_ROLE: String = "Admin"
    }

    @BeforeEach
    fun init() {
        userService = UserService(userRepository)
    }

    @Test
    fun `Should load user by username`() {
        val expectedAuthorities: Set<GrantedAuthority> = setOf(SimpleGrantedAuthority(ADMIN_ROLE))
        val expectedUsername = "admin"
        every { userRepository.findByUsername(expectedUsername) }
            .returns(User(1, "abc@gmail.com", expectedUsername, "password", setOf(Role(1, ADMIN_ROLE))))

        val user: UserDetails = userService.loadUserByUsername(expectedUsername)

        assertEquals(expectedUsername, user.username)
        assertEquals(expectedAuthorities, user.authorities)
    }

    @Test
    fun `Should throw UserNotAuthorizedExc when user does not exist`() {
        val expectedUsername = "user"
        every { userRepository.findByUsername(expectedUsername) }.returns(null)

        val exception = assertFailsWith<UserNotAuthorizedException> {
            userService.loadUserByUsername(expectedUsername)
        }

        assertEquals(exception.message, "User not authorized")
    }

    @Test
    fun `Should throw UserNotAuthorizedExc when user roles empty`() {
        val expectedUsername = "user"
        every { userRepository.findByUsername(expectedUsername) }
            .returns(User(1, "abc@gmail.com", expectedUsername, "password", emptySet()))

        val exception = assertFailsWith<UserNotAuthorizedException> {
            userService.loadUserByUsername(expectedUsername)
        }

        assertEquals(exception.message, "User not authorized")
    }
}
