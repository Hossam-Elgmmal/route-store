package com.route.network

import com.route.network.model.LoginRequest
import com.route.network.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val routeApi: RouteApi
) {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> =
        routeApi.login(loginRequest)

}