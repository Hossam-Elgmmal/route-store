package com.route.network

import com.route.network.model.AuthResponse
import com.route.network.model.DataResponse
import com.route.network.model.LoginRequest
import com.route.network.model.NetworkBrand
import com.route.network.model.NetworkCategory
import com.route.network.model.NetworkProduct
import com.route.network.model.NetworkSubCategory
import com.route.network.model.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RouteApi {

    @POST("auth/signin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<AuthResponse>

    @GET("categories")
    suspend fun getCategories(): DataResponse<List<NetworkCategory>>

    @GET("subcategories")
    suspend fun getSubCategories(): DataResponse<List<NetworkSubCategory>>

    @GET("brands")
    suspend fun getBrands(
        @Query("limit") limit: Int = 50,
    ): DataResponse<List<NetworkBrand>>

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 50,
    ): DataResponse<List<NetworkProduct>>

}