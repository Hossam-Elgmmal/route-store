package com.route.store.ui.auth.forgotpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.datastore.UserPreferencesRepository
import com.route.network.AuthRepository
import com.route.network.model.ForgotPasswordRequest
import com.route.network.model.ResetPasswordRequest
import com.route.network.model.VerifyCodeRequest
import com.route.store.ui.auth.AuthUiState
import com.route.store.ui.auth.EmailError
import com.route.store.ui.auth.PasswordError
import com.route.store.ui.auth.UiError
import com.route.store.ui.auth.validateEmail
import com.route.store.ui.auth.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val emailState = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    private var emailError = EmailError.NONE
    var isEmailError by mutableStateOf(false)

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState>
        get() = _uiState.asStateFlow()

    val isCodeSent = savedStateHandle.getStateFlow(key = IS_CODE_SENT, initialValue = false)
    val hasVerifiedCode =
        savedStateHandle.getStateFlow(key = HAS_VERIFIED_CODE, initialValue = false)

    var passwordValue by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var isPasswordVisible by mutableStateOf(false)
    private var passwordErrorSet = emptySet<PasswordError>()

    var rePasswordValue by mutableStateOf("")
    var isRePasswordError by mutableStateOf(false)
    var isRePasswordVisible by mutableStateOf(false)

    fun updateEmail(newEmail: String) {
        savedStateHandle[EMAIL] = newEmail
    }

    fun clearEmail() {
        savedStateHandle[EMAIL] = ""
    }

    fun resetUiState() {
        _uiState.update {
            AuthUiState.Idle
        }
    }

    fun updatePassword(newValue: String) {
        passwordValue = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun updateRePassword(newValue: String) {
        rePasswordValue = newValue
    }

    fun toggleRePasswordVisibility() {
        isRePasswordVisible = !isRePasswordVisible
    }

    fun sendVerificationCode(email: String) {

        emailError = validateEmail(email)
        isEmailError = emailError != EmailError.NONE
        if (isEmailError) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = AuthUiState.Loading

            _uiState.value = try {
                val response = authRepository.forgotPassword(
                    ForgotPasswordRequest(email = email)
                )
                if (response.isSuccessful) {
                    savedStateHandle[IS_CODE_SENT] = true
                    AuthUiState.Idle
                } else when (response.code()) {
                    404 -> AuthUiState.Error(UiError.EMAIL_NOT_FOUND)
                    else -> AuthUiState.Error(UiError.SERVER_ERROR)
                }
            } catch (e: CancellationException) {
                AuthUiState.Error(UiError.CANCELED)
                throw e
            } catch (e: IOException) {
                AuthUiState.Error(UiError.CONNECTION_ERROR)
            } catch (e: HttpException) {
                AuthUiState.Error(UiError.SERVER_ERROR)
            } catch (e: Exception) {
                AuthUiState.Error(UiError.UNEXPECTED_ERROR)
            }
        }
    }

    fun submitCode(code: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            _uiState.value = try {
                val response = authRepository.verifyResetCode(
                    VerifyCodeRequest(code)
                )
                if (response.isSuccessful) {
                    savedStateHandle[HAS_VERIFIED_CODE] = true
                    AuthUiState.Idle
                } else when (response.code()) {
                    400 -> AuthUiState.Error(UiError.RESET_CODE_INVALID)
                    else -> AuthUiState.Error(UiError.SERVER_ERROR)
                }
            } catch (e: CancellationException) {
                AuthUiState.Error(UiError.CANCELED)
                throw e
            } catch (e: IOException) {
                AuthUiState.Error(UiError.CONNECTION_ERROR)
            } catch (e: HttpException) {
                AuthUiState.Error(UiError.SERVER_ERROR)
            } catch (e: Exception) {
                AuthUiState.Error(UiError.UNEXPECTED_ERROR)
            }
        }
    }

    fun resetPassword(email: String) {
        passwordErrorSet = validatePassword(passwordValue)
        isPasswordError = passwordErrorSet.isNotEmpty()

        isRePasswordError = passwordValue != rePasswordValue

        if (isPasswordError || isRePasswordError) return

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            _uiState.value = try {
                val response = authRepository.resetPassword(
                    ResetPasswordRequest(email, passwordValue)
                )
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    userPreferencesRepository.setToken(token)
                    AuthUiState.Success
                } else when (response.code()) {
                    400 -> AuthUiState.Error(UiError.RESET_CODE_INVALID)
                    else -> AuthUiState.Error(UiError.SERVER_ERROR)
                }
            } catch (e: CancellationException) {
                AuthUiState.Error(UiError.CANCELED)
                throw e
            } catch (e: IOException) {
                AuthUiState.Error(UiError.CONNECTION_ERROR)
            } catch (e: HttpException) {
                AuthUiState.Error(UiError.SERVER_ERROR)
            } catch (e: Exception) {
                AuthUiState.Error(UiError.UNEXPECTED_ERROR)
            }
        }
    }

}

private const val EMAIL = "email"
private const val IS_CODE_SENT = "is_code_sent"
private const val HAS_VERIFIED_CODE = "has_verified_code"