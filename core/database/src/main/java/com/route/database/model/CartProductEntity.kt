package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartProducts")
data class CartProductEntity(

    @PrimaryKey
    val id: String,

    val count: Int,
)
