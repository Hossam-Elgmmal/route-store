package com.route.network

import com.route.network.model.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val routeApi: RouteApi
) {
    suspend fun login(loginRequest: LoginRequest) = routeApi.login(loginRequest)

}