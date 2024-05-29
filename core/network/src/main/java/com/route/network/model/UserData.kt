package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    )
