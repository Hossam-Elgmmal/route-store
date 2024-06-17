package com.route.data.model

data class Product(

    val id: String,

    val subCategoryId: String,
    val categoryId: String,
    val brandId: String,

    val title: String,
    val description: String,
    val imagesUrlList: List<String>,
    val imageCoverUrl: String,

    val ratingsQuantity: Int,
    val quantity: Int,
    val sold: Int,
    val price: Int,
    val ratingsAverage: Double,

    )