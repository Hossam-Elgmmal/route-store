package com.route.ecommerce.ui.screens.account

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.reposetory.OrderRepository
import com.route.datastore.UserData
import com.route.datastore.UserPreferencesRepository
import com.route.ecommerce.R
import com.route.ecommerce.ui.auth.EmailError
import com.route.ecommerce.ui.auth.PasswordError
import com.route.ecommerce.ui.auth.validateEmail
import com.route.ecommerce.ui.auth.validatePassword
import com.route.network.AuthRepository
import com.route.network.model.ChangePasswordRequest
import com.route.network.model.UpdatedInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var message by mutableStateOf(AccountMsg.NONE)

    var nameValue by mutableStateOf("")
    var isNameError by mutableStateOf(false)

    var newPasswordValue by mutableStateOf("")
    var isNewPasswordError by mutableStateOf(false)
    var isNewPasswordVisible by mutableStateOf(false)

    var rePasswordValue by mutableStateOf("")
    var isRePasswordError by mutableStateOf(false)
    var isRePasswordVisible by mutableStateOf(false)

    var passwordValue by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isPasswordError by mutableStateOf(false)
    private var passwordErrorSet = emptySet<PasswordError>()

    var phoneValue by mutableStateOf("")
    var isPhoneError by mutableStateOf(false)

    var emailValue by mutableStateOf("")
    var isEmailError by mutableStateOf(false)
    private var emailError = EmailError.NONE

    val accountUiState: StateFlow<AccountUiState> =
        userPreferencesRepository.userData
            .map {
                AccountUiState.Ready(
                    userData = it
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AccountUiState.Loading
            )

    fun signOut() {
        viewModelScope.launch {
            orderRepository.clearOrders()
            userPreferencesRepository.signOut()
        }
    }

    fun setUserImgName(fileName: String) {
        viewModelScope.launch {
            userPreferencesRepository.setUserImgName(fileName)
        }
    }

    fun updateNewPassword(newValue: String) {
        newPasswordValue = newValue
    }

    fun toggleNewPasswordVisibility() {
        isNewPasswordVisible = !isNewPasswordVisible
    }

    fun updateRePassword(newValue: String) {
        rePasswordValue = newValue
    }

    fun updatePassword(newValue: String) {
        passwordValue = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun toggleRePasswordVisibility() {
        isRePasswordVisible = !isRePasswordVisible
    }

    fun updateName(newValue: String) {
        nameValue = newValue
    }

    fun updateEmail(newValue: String) {
        emailValue = newValue
    }

    fun updatePhone(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            phoneValue = newValue
        }
    }

    fun clearName() {
        nameValue = ""
    }

    fun clearPhone() {
        phoneValue = ""
    }

    fun clearEmail() {
        emailValue = ""
    }

    fun resetMsg() {
        message = AccountMsg.NONE
    }

    fun cancelEditInfo() {
        clearName()
        clearPhone()
        clearEmail()
        isNameError = false
        isEmailError = false
        isPhoneError = false
    }

    fun cancelEditPassword() {
        updatePassword("")
        updateNewPassword("")
        updateRePassword("")
        isPasswordError = false
        isNewPasswordError = false
        isRePasswordError = false
    }

    fun updateInfo(token: String) {
        isNameError = nameValue.trim().isEmpty()

        emailError = validateEmail(emailValue)
        isEmailError = emailError != EmailError.NONE

        isPhoneError = phoneValue.length != 11

        if (isNameError || isEmailError || isPhoneError)
            return

        viewModelScope.launch(Dispatchers.IO) {
            message = AccountMsg.LOADING
            message = try {
                val response = authRepository
                    .updateInfo(
                        token = token,
                        updatedInfo = UpdatedInfo(
                            name = nameValue,
                            email = emailValue,
                            phone = phoneValue
                        )
                    )
                if (response.isSuccessful) {
                    userPreferencesRepository.updateUserInfo(
                        name = nameValue,
                        email = emailValue,
                        phone = phoneValue,
                    )
                    AccountMsg.SUCCESS
                } else {
                    when (response.code()) {
                        400 -> AccountMsg.EMAIL_EXISTS
                        else -> AccountMsg.SERVER_ERROR
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                AccountMsg.CONNECTION_ERROR
            } catch (e: HttpException) {
                AccountMsg.SERVER_ERROR
            } catch (e: Exception) {
                AccountMsg.UNEXPECTED_ERROR
            }
        }
    }

    fun changePassword(token: String, password: String) {
        passwordErrorSet = validatePassword(newPasswordValue)
        isPasswordError = passwordValue != password
        isNewPasswordError = passwordErrorSet.isNotEmpty()

        isRePasswordError = newPasswordValue != rePasswordValue

        if (isPasswordError || isNewPasswordError || isRePasswordError)
            return

        viewModelScope.launch(Dispatchers.IO) {
            message = AccountMsg.LOADING
            message = try {
                val response = authRepository
                    .changePassword(
                        token = token,
                        changePasswordRequest = ChangePasswordRequest(
                            currentPassword = passwordValue,
                            newPassword = newPasswordValue,
                            rePassword = rePasswordValue
                        )
                    )
                if (response.isSuccessful) {
                    val newToken = response.body()?.token ?: ""
                    userPreferencesRepository.updateUserPassword(
                        newToken = newToken,
                        password = newPasswordValue
                    )
                    AccountMsg.SUCCESS
                } else {
                    when (response.code()) {
                        401 -> AccountMsg.EXPIRED_TOKEN
                        else -> AccountMsg.SERVER_ERROR
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                AccountMsg.CONNECTION_ERROR
            } catch (e: HttpException) {
                AccountMsg.SERVER_ERROR
            } catch (e: Exception) {
                AccountMsg.UNEXPECTED_ERROR
            }
        }
    }
}

sealed interface AccountUiState {
    data object Loading : AccountUiState
    data class Ready(val userData: UserData) : AccountUiState
}

enum class AccountMsg(
    @StringRes val msgId: Int
) {
    NONE(R.string.empty),
    LOADING(R.string.empty),
    SUCCESS(R.string.success),
    CONNECTION_ERROR(R.string.connection_error),
    SERVER_ERROR(R.string.server_error),
    UNEXPECTED_ERROR(R.string.unexpected_error),
    EMAIL_EXISTS(R.string.email_already_exists),
    EXPIRED_TOKEN(R.string.expired_token_please_login_again)
}