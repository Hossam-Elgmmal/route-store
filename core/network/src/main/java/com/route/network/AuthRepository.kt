package com.route.network

import com.route.network.model.AuthResponse
import com.route.network.model.LoginRequest
import com.route.network.model.SignUpRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val routeApi: RouteApi
) {
    suspend fun login(loginRequest: LoginRequest): Response<AuthResponse> =
        routeApi.login(loginRequest)

    suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthResponse> =
        routeApi.signUp(signUpRequest)

}