package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductToAddCart(
    @SerialName("productId")
    val id: String,
)

@Serializable
data class ProductCount(
    @SerialName("count")
    val count: Int,
)

@Serializable
data class NetworkCartProduct(
    @SerialName("count")
    val count: Int,
    @SerialName("product")
    val product: CartProductId,
)

@Serializable
data class CartProductId(
    @SerialName("id")
    val id: String,
)
