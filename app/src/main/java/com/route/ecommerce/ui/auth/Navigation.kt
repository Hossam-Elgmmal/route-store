package com.route.ecommerce.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.auth.login.LoginScreen
import com.route.ecommerce.ui.auth.signup.SignupScreen

const val LOGIN_ROUTE = "LOGIN"
const val SIGNUP_ROUTE = "SIGNUP"
const val FORGOT_PASSWORD_ROUTE = "FORGOT_PASSWORD"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LOGIN_ROUTE, navOptions)

fun NavController.navigateToSignup(navOptions: NavOptions? = null) =
    navigate(SIGNUP_ROUTE, navOptions)

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) =
    navigate(FORGOT_PASSWORD_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen(
    appState: EcomAppState
) {
    composable(route = LOGIN_ROUTE) {
        LoginScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.signupScreen(
    appState: EcomAppState
) {
    composable(route = SIGNUP_ROUTE) {
        SignupScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.forgotPasswordScreen(
    appState: EcomAppState
) {
    composable(route = FORGOT_PASSWORD_ROUTE) {
        ForgotPasswordScreen(
            appState = appState
        )
    }
}
