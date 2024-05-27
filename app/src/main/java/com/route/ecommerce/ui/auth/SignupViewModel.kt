package com.route.ecommerce.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignupViewModel @Inject constructor() : ViewModel() {

    var emailValue by mutableStateOf("")
    var isEmailError by mutableStateOf(false)

    var passwordValue by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var isPasswordVisible by mutableStateOf(false)

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

    fun signup() {
        // todo
    }
}