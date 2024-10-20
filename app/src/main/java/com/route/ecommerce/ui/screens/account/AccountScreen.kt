package com.route.ecommerce.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import kotlinx.coroutines.CoroutineScope

@Composable
fun AccountScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val accountUiState by viewModel.accountUiState.collectAsState()


    BackHandler {
        onBackPressed()
    }
    when (accountUiState) {
        AccountUiState.Loading -> {}
        is AccountUiState.Ready -> {
            val userData = (accountUiState as AccountUiState.Ready).userData

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (userData.userToken.isEmpty()) { // user not logged in
                    item {
                        SignedOutScreen(appState)
                    }

                } else {
                    item {
                        SignedInScreen(coroutineScope, viewModel, userData)
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}
