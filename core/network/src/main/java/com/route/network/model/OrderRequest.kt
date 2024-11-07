package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(

    @SerialName("shippingAddress")
    val shippingAddress: ShippingAddress,
)
