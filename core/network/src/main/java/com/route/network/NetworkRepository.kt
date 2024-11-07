package com.route.network

import com.route.network.model.Cart
import com.route.network.model.NetworkBrand
import com.route.network.model.NetworkCategory
import com.route.network.model.NetworkOrder
import com.route.network.model.NetworkProduct
import com.route.network.model.NetworkSubCategory
import com.route.network.model.OrderRequest
import com.route.network.model.ProductCount
import com.route.network.model.ProductToAddCart
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
    ): Boolean =
        try {
            val response = routeApi.addProductToCart(token, ProductToAddCart(productId))
            response.isSuccessful
        } catch (_: Exception) {
            false
        }

    suspend fun updateProductCount(
        token: String,
        count: Int,
        productId: String
    ): Boolean =
        try {
            val response = routeApi.updateProductCount(token, ProductCount(count), productId)
            response.isSuccessful
        } catch (_: Exception) {
            false
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
    ): Boolean =
        try {
            val response = routeApi.removeCartItem(token, productId)
            response.isSuccessful
        } catch (_: Exception) {
            false
        }

    suspend fun createCashOrder(
        token: String,
        cartId: String,
        orderRequest: OrderRequest,
    ): Boolean =
        try {
            val response = routeApi.createCashOrder(token, orderRequest, cartId)
            response.isSuccessful
        } catch (_: Exception) {
            false
        }

    suspend fun getUserOrders(
        userId: String
    ): List<NetworkOrder> =
        try {
            routeApi.getUserOrders(userId)
        } catch (_: Exception) {
            emptyList()
        }

}