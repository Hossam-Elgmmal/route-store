package com.route.network

import com.route.network.model.AuthResponse
import com.route.network.model.ChangePasswordRequest
import com.route.network.model.ForgotPasswordRequest
import com.route.network.model.LoginRequest
import com.route.network.model.ResetPasswordRequest
import com.route.network.model.SignUpRequest
import com.route.network.model.UpdatedInfo
import com.route.network.model.VerifyCodeRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val routeApi: RouteApi
) {
    suspend fun login(loginRequest: LoginRequest): Response<AuthResponse> =
        routeApi.login(loginRequest)

    suspend fun signUp(signUpRequest: SignUpRequest): Response<AuthResponse> =
        routeApi.signUp(signUpRequest)

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) =
        routeApi.forgotPassword(forgotPasswordRequest)

    suspend fun verifyResetCode(verifyCodeRequest: VerifyCodeRequest) =
        routeApi.verifyResetCode(verifyCodeRequest)

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest) =
        routeApi.resetPassword(resetPasswordRequest)

    suspend fun updateInfo(token: String, updatedInfo: UpdatedInfo) =
        routeApi.updateUserInfo(token = token, updatedInfo = updatedInfo)

    suspend fun changePassword(token: String, changePasswordRequest: ChangePasswordRequest) =
        routeApi.changeUserPassword(token = token, changePasswordRequest = changePasswordRequest)

}