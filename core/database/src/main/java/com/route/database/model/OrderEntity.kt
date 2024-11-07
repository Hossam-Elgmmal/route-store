package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(

    @PrimaryKey
    val id: String,
    val address: String,
    val phone: String,
    val city: String,
    val taxPrice: Int,

    val shippingPrice: Int,
    val totalOrderPrice: Int,
    val paymentMethodType: String,

    val isPaid: Boolean,
    val isDelivered: Boolean,
    val cartItemsText: String,

    val createdAt: String,

    )
