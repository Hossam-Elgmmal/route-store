package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCategory(

    @SerialName("_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("image")
    val imageUrl: String,

    )
