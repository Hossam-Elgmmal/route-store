package com.route.model

data class UserData(
    val darkTheme: DarkTheme,
)

enum class DarkTheme {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}
