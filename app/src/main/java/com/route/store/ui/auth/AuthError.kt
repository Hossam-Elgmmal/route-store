package com.route.store.ui.auth

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.route.store.R
import com.route.store.navigation.TopLevelDestination
import com.route.store.ui.components.EcomErrorDialog
import com.route.store.ui.components.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class EmailError {
    NONE, INVALID
}

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val uiError: UiError) : AuthUiState
}

enum class UiError(
    @StringRes val errorMessageId: Int
) {
    WRONG_EMAIL_OR_PASSWORD(
        errorMessageId = R.string.wrong_email_or_password
    ),
    ACCOUNT_ALREADY_EXISTS(
        errorMessageId = R.string.account_already_exists
    ),
    CANCELED(
        errorMessageId = R.string.request_canceled
    ),
    CONNECTION_ERROR(
        errorMessageId = R.string.connection_error
    ),
    SERVER_ERROR(
        errorMessageId = R.string.server_error
    ),
    UNEXPECTED_ERROR(
        errorMessageId = R.string.unexpected_error
    ),
    EMAIL_NOT_FOUND(
        errorMessageId = R.string.email_not_found
    ),
    RESET_CODE_INVALID(
        errorMessageId = R.string.reset_code_is_invalid_or_has_expired
    )
}

enum class PasswordError {
    TOO_SHORT, NO_DIGITS, NO_UPPERCASE, NO_LOWERCASE
}

fun validateEmail(emailValue: String): EmailError {

    var emailError = EmailError.NONE

    if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
        emailError = EmailError.INVALID
    }

    return emailError
}

fun validatePassword(passwordValue: String): Set<PasswordError> {

    val passwordErrorSet = mutableSetOf<PasswordError>()

    if (passwordValue.length < 8) {
        passwordErrorSet.add(PasswordError.TOO_SHORT)
    }
    if (!passwordValue.any { it.isDigit() }) {
        passwordErrorSet.add(PasswordError.NO_DIGITS)
    }
    if (!passwordValue.any { it.isUpperCase() }) {
        passwordErrorSet.add(PasswordError.NO_UPPERCASE)
    }
    if (!passwordValue.any { it.isLowerCase() }) {
        passwordErrorSet.add(PasswordError.NO_LOWERCASE)
    }
    return passwordErrorSet
}

@Composable
fun AuthUiEvents(
    authUiState: AuthUiState,
    onSuccess: (TopLevelDestination, Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    coroutineScope: CoroutineScope,
) {
    when (authUiState) {
        AuthUiState.Idle -> {}
        is AuthUiState.Error -> {
            EcomErrorDialog(
                iconId = R.drawable.ic_error,
                textId = authUiState.uiError.errorMessageId,
                onDismissRequest = onDismissRequest
            )
        }

        AuthUiState.Loading -> {
            LoadingDialog()
        }

        AuthUiState.Success -> {
            EcomErrorDialog(
                iconId = R.drawable.ic_success,
                textId = R.string.success,
                onDismissRequest = {},
            )
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    delay(2_000)
                    onSuccess(TopLevelDestination.ACCOUNT, true)
                }
            }
        }
    }
}