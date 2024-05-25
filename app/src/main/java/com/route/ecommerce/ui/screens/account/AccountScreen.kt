package com.route.ecommerce.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountScreen(
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit,
    navigateToWishlist: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val accountUiState by viewModel.accountUiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = navigateToLogin) {
            Text(text = "login")
        }
        Button(onClick = navigateToSignup) {
            Text(text = "sign up")
        }
        Button(onClick = navigateToWishlist) {
            Text(text = "wishlist")
        }

        ThemeDialogButton(
            accountUiState = accountUiState,
            setDarkTheme = viewModel::setDarkTheme
        )

    }
}