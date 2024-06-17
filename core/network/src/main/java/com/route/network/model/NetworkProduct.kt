package com.route.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkProduct(

    @SerialName("sold")
    val sold: Int,

    @SerialName("images")
    val images: List<String>,

    @SerialName("subcategory")
    val subcategoryList: List<NetworkSubCategory>,

    @SerialName("ratingsQuantity")
    val ratingsQuantity: Int,

    @SerialName("_id")
    val id: String,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("price")
    val price: Int,

    @SerialName("imageCover")
    val imageCover: String,

    @SerialName("category")
    val networkCategory: NetworkCategory,

    @SerialName("brand")
    val networkBrand: NetworkBrand,

    @SerialName("ratingsAverage")
    val ratingsAverage: Double,

    )
