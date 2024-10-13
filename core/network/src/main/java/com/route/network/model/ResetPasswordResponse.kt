package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(

    @SerialName("token")
    val token: String
)
