package com.route.ecommerce.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState

@Composable
fun AccountScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val accountUiState by viewModel.accountUiState.collectAsState()

    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(onClick = appState::navigateToLogin) {
            Text(text = "to login")
        }
        Button(onClick = appState::navigateToSignup) {
            Text(text = "to signup")
        }
        Button(onClick = appState::navigateToWishlist) {
            Text(text = "wishlist")
        }
        DarkThemeButton(
            accountUiState = accountUiState,
            setDarkTheme = viewModel::setDarkTheme
        )
    }
}