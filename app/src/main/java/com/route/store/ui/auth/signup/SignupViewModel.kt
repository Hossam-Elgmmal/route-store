package com.route.store.ui.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.datastore.UserInfo
import com.route.datastore.UserPreferencesRepository
import com.route.network.AuthRepository
import com.route.network.model.SignUpRequest
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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var nameValue by mutableStateOf("")
    var isNameError by mutableStateOf(false)

    var emailValue by mutableStateOf("")
    var isEmailError by mutableStateOf(false)
    private var emailError = EmailError.NONE

    var passwordValue by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var isPasswordVisible by mutableStateOf(false)
    private var passwordErrorSet = emptySet<PasswordError>()

    var rePasswordValue by mutableStateOf("")
    var isRePasswordError by mutableStateOf(false)
    var isRePasswordVisible by mutableStateOf(false)

    private val _signUpUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val signUpUiState: StateFlow<AuthUiState>
        get() = _signUpUiState.asStateFlow()

    fun resetUiState() {
        _signUpUiState.value = AuthUiState.Idle
    }

    fun updateName(newValue: String) {
        nameValue = newValue
    }

    fun updateEmail(newValue: String) {
        emailValue = newValue
    }

    fun clearName() {
        nameValue = ""
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

    fun updateRePassword(newValue: String) {
        rePasswordValue = newValue
    }

    fun toggleRePasswordVisibility() {
        isRePasswordVisible = !isRePasswordVisible
    }

    fun signup() {
        isNameError = nameValue.trim().isEmpty()

        emailError = validateEmail(emailValue)
        isEmailError = emailError != EmailError.NONE

        passwordErrorSet = validatePassword(passwordValue)
        isPasswordError = passwordErrorSet.isNotEmpty()

        isRePasswordError = passwordValue != rePasswordValue

        if (isNameError || isEmailError || isPasswordError || isRePasswordError)
            return

        viewModelScope.launch(Dispatchers.IO) {
            _signUpUiState.value = AuthUiState.Loading
            _signUpUiState.value =
                try {
                    val response = authRepository
                        .signUp(
                            SignUpRequest(
                                name = nameValue,
                                email = emailValue,
                                password = passwordValue,
                                rePassword = rePasswordValue
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
                        AuthUiState.Success

                    } else when (response.code()) {
                        409 -> AuthUiState.Error(UiError.ACCOUNT_ALREADY_EXISTS)
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