package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(

    @SerialName("message")
    val message: String,

    @SerialName("user")
    val userData: UserData,

    @SerialName("token")
    val token: String,

    )
