package com.route.ecommerce.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import kotlinx.coroutines.delay

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

                    val mottoList = listOf(
                        R.string.shop_anytime_anywhere,
                        R.string.shop_smart_save_time,
                        R.string.your_online_shopping_destination,
                        R.string.effortless_shopping_endless_possibilities,
                    )
                    item {
                        var currentIndex by rememberSaveable { mutableIntStateOf(0) }
                        LaunchedEffect(Unit) {
                            while (true) {
                                delay(4_000)
                                currentIndex = (currentIndex + 1) % mottoList.size
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)

                        ) {
                            Text(
                                text = stringResource(R.string.welcome_to_route),
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                            )
                            AnimatedContent(
                                targetState = currentIndex,
                                label = "motto"
                            ) { index ->
                                Text(
                                    text = stringResource(mottoList[index]),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
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
                            Spacer(modifier = Modifier.height(8.dp))
                            IconTextRow(
                                textId = R.string.first_order_get_free_shipping,
                                iconId = R.drawable.ic_free_delivery
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            IconTextRow(
                                textId = R.string.returns_made_easy,
                                iconId = R.drawable.ic_easy_return
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            IconTextRow(
                                textId = R.string.exclusive_deals_tailored_to_your_preferences,
                                iconId = R.drawable.ic_quality
                            )
                        }
                    }

                } else {
                    item {
                        ////////////////
                        TextButton(
                            onClick = viewModel::signOut
                        ) {
                            Text(stringResource(R.string.sign_out))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconTextRow(
    @StringRes textId: Int,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}