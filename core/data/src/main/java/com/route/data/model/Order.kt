package com.route.data.model

data class Order(

    val id: String,
    val address: String,
    val city: String,
    val phone: String,
    val taxPrice: Int,

    val shippingPrice: Int,
    val totalOrderPrice: Int,
    val cartItems: Map<String, String>,

    val paymentMethodType: String,

    val isPaid: Boolean,
    val isDelivered: Boolean,
    val createdAt: String,

    )
