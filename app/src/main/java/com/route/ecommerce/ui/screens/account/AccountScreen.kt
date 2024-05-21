package com.route.ecommerce.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val accountUiState by viewModel.accountUiState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Account Screen")
        when (accountUiState) {
            AccountUiState.Loading -> Text(text = "Loading homeUiState")
            is AccountUiState.Ready -> {
                DarkThemeSwitch(
                    isChecked = (accountUiState as AccountUiState.Ready).darkTheme,
                    toggleDarkTheme = viewModel::toggleDarkTheme,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DarkThemeSwitch(
    isChecked: Boolean,
    toggleDarkTheme: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Theme")
        Switch(
            checked = isChecked,
            onCheckedChange = { toggleDarkTheme(it) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}