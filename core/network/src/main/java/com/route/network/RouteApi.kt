package com.route.network

import com.route.network.model.AuthResponse
import com.route.network.model.LoginRequest
import com.route.network.model.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RouteApi {

    @POST("auth/signin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<AuthResponse>

}