package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeRequest(

    @SerialName("resetCode")
    val resetCode: String
)
