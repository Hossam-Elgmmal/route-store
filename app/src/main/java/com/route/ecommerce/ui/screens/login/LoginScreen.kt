package com.route.ecommerce.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    navigateToSignup: () -> Unit,
    navigateToAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Login")
        Button(onClick = navigateToSignup) {
            Text(text = "sign up")
        }
        Button(
            onClick = navigateToAccount
        ) {
            Text(text = "Account")
        }
    }
}