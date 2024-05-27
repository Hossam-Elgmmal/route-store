package com.route.ecommerce.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState

@Composable
fun SignupScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "signup")
    }
}