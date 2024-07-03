package com.route.network

import com.route.network.model.AddedCart
import com.route.network.model.AuthResponse
import com.route.network.model.Cart
import com.route.network.model.DataResponse
import com.route.network.model.LoginRequest
import com.route.network.model.NetworkBrand
import com.route.network.model.NetworkCategory
import com.route.network.model.NetworkProduct
import com.route.network.model.NetworkSubCategory
import com.route.network.model.ProductCount
import com.route.network.model.ProductToAddCart
import com.route.network.model.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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

    @POST("cart")
    suspend fun addProductToCart(
        @Header("token") token: String,
        @Body product: ProductToAddCart,
    ): DataResponse<AddedCart>

    @PUT("cart/{id}")
    suspend fun updateProductCount(
        @Header("token") token: String,
        @Body product: ProductCount,
        @Path("id") productId: String,
    ): DataResponse<AddedCart>

    @GET("cart")
    suspend fun getUserCart(
        @Header("token") token: String,
    ): DataResponse<Cart>

    @DELETE("cart/{id}")
    suspend fun removeCartItem(
        @Header("token") token: String,
        @Path("id") productId: String,
    ): DataResponse<AddedCart>

}