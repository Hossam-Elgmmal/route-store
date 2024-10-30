package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(

    @SerialName("currentPassword")
    val currentPassword: String,

    @SerialName("password")
    val newPassword: String,

    @SerialName("rePassword")
    val rePassword: String,
)
