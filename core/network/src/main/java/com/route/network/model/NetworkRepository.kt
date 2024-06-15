package com.route.network.model

import com.route.network.RouteApi
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val routeApi: RouteApi
) {

    suspend fun getCategories(): List<Category> {
        return try {
            routeApi.getCategories().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSubCategories(): List<SubCategory> {
        return try {
            routeApi.getSubCategories().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getBrands(): List<Brand> {
        return try {
            routeApi.getBrands().data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getProducts(): List<Product> {
        return try {
            routeApi.getProducts().data
        } catch (e: Exception) {
            emptyList()
        }
    }

}