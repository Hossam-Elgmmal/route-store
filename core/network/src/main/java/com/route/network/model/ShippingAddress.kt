package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddress(

    @SerialName("details")
    val details: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("city")
    val city: String,
)
