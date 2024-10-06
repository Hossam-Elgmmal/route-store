package com.route.ecommerce.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
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
    when (accountUiState) {
        AccountUiState.Loading -> {}
        is AccountUiState.Ready -> {
            val readyUiState = accountUiState as AccountUiState.Ready
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (readyUiState.userData.userToken.isEmpty()) { // user not logged in
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)

                        ) {
                            Text(
                                text = stringResource(R.string.welcome_to_route),
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = appState::navigateToSignup,
                                modifier = Modifier.fillMaxWidth(),
                                shape = CircleShape,
                                contentPadding = PaddingValues(12.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.create_account),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 18.sp
                                )
                            }
                            OutlinedButton(
                                onClick = appState::navigateToLogin,
                                modifier = Modifier.fillMaxWidth(),
                                shape = CircleShape,
                                contentPadding = PaddingValues(12.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.sign_in),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontSize = 18.sp
                                )
                            }
                        }

                    }

                } else {


                }
            }
        }
    }
}