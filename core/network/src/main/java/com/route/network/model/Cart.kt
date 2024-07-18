package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddedCart(

    @SerialName("_id")
    val cartId: String,

    @SerialName("cartOwner")
    val ownerId: String,

    )

@Serializable
data class Cart(

    @SerialName("_id")
    val cartId: String,

    @SerialName("cartOwner")
    val ownerId: String,

    @SerialName("products")
    val products: List<NetworkCartProduct>,

    )
