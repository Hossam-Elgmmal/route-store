package com.route.ecommerce.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.datastore.UserPreferencesRepository
import com.route.ecommerce.ui.auth.EmailError
import com.route.ecommerce.ui.auth.PasswordError
import com.route.ecommerce.ui.auth.UiError
import com.route.ecommerce.ui.auth.validateEmail
import com.route.ecommerce.ui.auth.validatePassword
import com.route.model.UserInfo
import com.route.network.AuthRepository
import com.route.network.model.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var emailValue by mutableStateOf("")
    private var emailError = EmailError.NONE
    var isEmailError by mutableStateOf(false)

    var passwordValue by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    private var passwordError = emptySet<PasswordError>()
    var isPasswordError by mutableStateOf(false)

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState>
        get() = _loginUiState.asStateFlow()

    fun resetUiState() {
        _loginUiState.value = LoginUiState.Idle
    }

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
        emailError = validateEmail(emailValue)
        isEmailError = emailError != EmailError.NONE

        passwordError = validatePassword(passwordValue)
        isPasswordError = passwordError.isNotEmpty()

        if (isEmailError || isPasswordError) return

        viewModelScope.launch(Dispatchers.IO) {
            _loginUiState.value = LoginUiState.Loading
            _loginUiState.value =
                try {
                    val response = authRepository
                        .login(
                            LoginRequest(
                                email = emailValue,
                                password = passwordValue
                            )
                        )
                    if (response.isSuccessful) {
                        val userInfo = UserInfo(
                            name = response.body()?.userData?.name ?: "",
                            email = emailValue,
                            password = passwordValue,
                            token = response.body()?.token ?: "",
                        )
                        userPreferencesRepository.setUserInfo(userInfo)
                        LoginUiState.Success(userInfo)

                    } else when (response.code()) {
                        401 -> LoginUiState.Error(UiError.WRONG_EMAIL_OR_PASSWORD)
                        else -> LoginUiState.Error(UiError.SERVER_ERROR)
                    }

                } catch (e: CancellationException) {
                    LoginUiState.Error(UiError.CANCELED)
                    throw e
                } catch (e: IOException) {
                    LoginUiState.Error(UiError.CONNECTION_ERROR)
                } catch (e: HttpException) {
                    LoginUiState.Error(UiError.SERVER_ERROR)
                } catch (e: Exception) {
                    LoginUiState.Error(UiError.UNEXPECTED_ERROR)
                }
        }
    }
}

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val userInfo: UserInfo) : LoginUiState
    data class Error(val uiError: UiError) : LoginUiState
}
