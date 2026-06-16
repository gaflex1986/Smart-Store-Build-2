package com.example.data

enum class AccountRole {
    REGULAR, DEVELOPER, BUSINESS
}

data class LocalAccount(
    val email: String,
    val role: AccountRole,
    val passport: String = "",
    val businessDocs: String = ""
)


