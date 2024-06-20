package com.route.ecommerce.ui.auth

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.route.ecommerce.R
import com.route.ecommerce.navigation.TopLevelDestination
import com.route.ecommerce.ui.components.EcomErrorDialog
import com.route.ecommerce.ui.components.LoadingDialog


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
            onSuccess(TopLevelDestination.ACCOUNT, true)
        }
    }
}