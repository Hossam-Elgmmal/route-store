package com.route.ecommerce.ui.auth

import android.util.Patterns
import androidx.annotation.StringRes
import com.route.ecommerce.R


enum class EmailError {
    NONE, INVALID
}

enum class UiError(
    @StringRes val errorMessageId: Int
) {
    WRONG_EMAIL_OR_PASSWORD(
        errorMessageId = R.string.wrong_email_or_password
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

    val passwordError = mutableSetOf<PasswordError>()

    if (passwordValue.length < 8) {
        passwordError.add(PasswordError.TOO_SHORT)
    }
    if (!passwordValue.any { it.isDigit() }) {
        passwordError.add(PasswordError.NO_DIGITS)
    }
    if (!passwordValue.any { it.isUpperCase() }) {
        passwordError.add(PasswordError.NO_UPPERCASE)
    }
    if (!passwordValue.any { it.isLowerCase() }) {
        passwordError.add(PasswordError.NO_LOWERCASE)
    }
    return passwordError
}
