package com.route.network.model

import com.route.network.RouteApi
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val routeApi: RouteApi
) {

    suspend fun getCategories(): List<NetworkCategory> {
        return try {
            routeApi.getCategories().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSubCategories(): List<NetworkSubCategory> {
        return try {
            routeApi.getSubCategories().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getBrands(): List<NetworkBrand> {
        return try {
            routeApi.getBrands().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getProducts(): List<NetworkProduct> {
        return try {
            routeApi.getProducts().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addProductToCart(
        token: String,
        productId: String
    ): AddedCart =
        try {
            routeApi.addProductToCart(token, ProductToAddCart(productId)).data
        } catch (_: Exception) {
            AddedCart("", "")
        }

    suspend fun updateProductCount(
        token: String,
        count: Int,
        productId: String
    ): AddedCart =
        try {
            routeApi.updateProductCount(token, ProductCount(count), productId).data
        } catch (_: Exception) {
            AddedCart("", "")
        }

    suspend fun getUserCart(
        token: String
    ): Cart =
        try {
            routeApi.getUserCart(token).data
        } catch (_: Exception) {
            Cart("", "", emptyList())
        }

    suspend fun removeCartItem(
        token: String,
        productId: String
    ): AddedCart =
        try {
            routeApi.removeCartItem(token, productId).data
        } catch (_: Exception) {
            AddedCart("", "")
        }

}