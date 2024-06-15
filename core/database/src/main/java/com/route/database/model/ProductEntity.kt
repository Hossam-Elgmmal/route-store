package com.route.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey val id: String,

    val subCategoryId: String,
    val categoryId: String,
    val brandId: String,

    val title: String,
    val description: String,
    val imagesUrl: String,
    val imageCoverUrl: String,

    val searchText: String,

    val ratingsQuantity: Int,
    val quantity: Int,
    val sold: Int,
    val price: Int,
    val ratingsAverage: Double,

    )
