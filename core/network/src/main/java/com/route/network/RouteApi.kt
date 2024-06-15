package com.route.network

import com.route.network.model.AuthResponse
import com.route.network.model.Brand
import com.route.network.model.Category
import com.route.network.model.DataResponse
import com.route.network.model.LoginRequest
import com.route.network.model.Product
import com.route.network.model.SignUpRequest
import com.route.network.model.SubCategory
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
    suspend fun getCategories(): DataResponse<List<Category>>

    @GET("subcategories")
    suspend fun getSubCategories(): DataResponse<List<SubCategory>>

    @GET("brands")
    suspend fun getBrands(
        @Query("limit") limit: Int = 50,
    ): DataResponse<List<Brand>>

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 50,
    ): DataResponse<List<Product>>

}