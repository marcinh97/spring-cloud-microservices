package com.horakm.gateway.auth.model

data class User(val id: Int,
                val email: String,
                val username: String,
                val password: String,
                val roles: Set<Role>,
                val isExpired: Boolean = false,
                val isLocked: Boolean = false,
                val isCredentialExpired: Boolean = false,
                val isEnabled: Boolean = true,
)
