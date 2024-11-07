package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkOrder(

    @SerialName("_id")
    val id: String,

    @SerialName("shippingAddress")
    val shippingAddress: ShippingAddress,

    @SerialName("taxPrice")
    val taxPrice: Int,

    @SerialName("shippingPrice")
    val shippingPrice: Int,

    @SerialName("totalOrderPrice")
    val totalOrderPrice: Int,

    @SerialName("paymentMethodType")
    val paymentMethodType: String,

    @SerialName("isPaid")
    val isPaid: Boolean,

    @SerialName("isDelivered")
    val isDelivered: Boolean,

    @SerialName("cartItems")
    val cartItems: List<NetworkCartProduct>,

    @SerialName("createdAt")
    val createdAt: String,

    )
