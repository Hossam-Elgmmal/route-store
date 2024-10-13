package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(

    @SerialName("statusMsg")
    val statusMsg: String
)
