package com.route.ecommerce.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.network.AuthRepository
import com.route.network.model.LoginRequest
import com.route.network.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var emailValue by mutableStateOf("")
    var isEmailError by mutableStateOf(false)

    var passwordValue by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var isPasswordVisible by mutableStateOf(false)

    var uiState: LoginUiState by mutableStateOf(LoginUiState.Loading)

    fun updateEmail(newValue: String) {
        emailValue = newValue
    }

    fun clearEmail() {
        emailValue = ""
    }

    fun updatePassword(newValue: String) {
        passwordValue = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun login() {
        viewModelScope.launch {
            val response = authRepository.login(LoginRequest(emailValue, passwordValue))
            uiState = LoginUiState.Success(response)
        }
    }
}

sealed interface LoginUiState {
    data object Loading : LoginUiState
    data object Ready : LoginUiState
    data class Success(val response: LoginResponse) : LoginUiState
    data object Error : LoginUiState
}