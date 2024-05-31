package com.route.model

data class UserData(
    val darkTheme: DarkTheme,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userToken: String,
)

enum class DarkTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}

data class UserInfo(
    val name: String,
    val email: String,
    val password: String,
    val token: String,
)