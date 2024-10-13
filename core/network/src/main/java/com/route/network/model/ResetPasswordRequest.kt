package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(

    @SerialName("email")
    val email: String,

    @SerialName("newPassword")
    val password: String,
)
