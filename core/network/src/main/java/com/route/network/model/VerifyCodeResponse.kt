package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeResponse(

    @SerialName("status")
    val status: String
)
