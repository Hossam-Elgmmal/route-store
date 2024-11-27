package com.route.store.ui.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navOptions
import com.route.store.R
import com.route.store.navigation.TopLevelDestination
import com.route.store.ui.EcomAppState
import com.route.store.ui.components.EcomErrorDialog
import com.route.store.ui.components.EcomTextField
import com.route.store.ui.components.LoadingDialog
import com.route.store.ui.components.PriceText
import com.route.store.ui.theme.successIconColor

@Composable
fun CheckoutScreen(
    appState: EcomAppState,
    cartId: String,
    subtotal: Int,
    items: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.shipping_details),
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp)
            )
        }
        item {
            EcomTextField(
                value = viewModel.city,
                onValueChange = viewModel::updateCity,
                labelId = R.string.city,
                supportTextId =
                if (viewModel.isCityError) R.string.cannot_be_empty
                else R.string.sign_up_username_support,
                leadingIconId = R.drawable.ic_location,
                trailingIconId =
                if (viewModel.city.isEmpty()) null
                else R.drawable.ic_clear,
                trailingIconAction = viewModel::clearCity,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = VisualTransformation.None,
                isError = viewModel.isCityError,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            EcomTextField(
                value = viewModel.address,
                onValueChange = viewModel::updateAddress,
                labelId = R.string.address,
                supportTextId =
                if (viewModel.isAddressError) R.string.cannot_be_empty
                else R.string.sign_up_username_support,
                leadingIconId = R.drawable.ic_location,
                trailingIconId =
                if (viewModel.address.isEmpty()) null
                else R.drawable.ic_clear,
                trailingIconAction = viewModel::clearAddress,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                visualTransformation = VisualTransformation.None,
                isError = viewModel.isAddressError,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            EcomTextField(
                value = viewModel.phone,
                onValueChange = viewModel::updatePhone,
                labelId = R.string.phone_number,
                supportTextId =
                if (viewModel.isPhoneError) R.string.please_enter_a_valid_phone_number
                else R.string.phone_support_value,
                leadingIconId = R.drawable.ic_phone,
                trailingIconId =
                if (viewModel.phone.isEmpty()) null
                else R.drawable.ic_clear,
                trailingIconAction = viewModel::clearPhone,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = VisualTransformation.None,
                isError = viewModel.isPhoneError,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedCard(
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.payment_method),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = stringResource(R.string.cash_on_delivery),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.items),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = items.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.subtotal),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        PriceText(
                            price = subtotal,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.shipping_fee),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = stringResource(R.string.free),
                            style = MaterialTheme.typography.labelLarge,
                            color = successIconColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        PriceText(
                            price = subtotal,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                }
            }
        }
        item {
            Button(
                onClick = {
                    viewModel.createOrder(cartId)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.submit_order),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
    when (viewModel.uiState) {
        CheckoutUiState.Idle -> {}
        CheckoutUiState.Error -> {
            EcomErrorDialog(
                iconId = R.drawable.ic_error,
                textId = R.string.unexpected_error,
                onDismissRequest = {
                    viewModel.resetUiState()
                },
            )
        }

        CheckoutUiState.Loading -> {
            LoadingDialog()
        }

        CheckoutUiState.Success -> {
            EcomErrorDialog(
                iconId = R.drawable.ic_success,
                textId = R.string.order_made_successfully,
                onDismissRequest = {
                    appState.navigateToOrders(
                        navOptions {
                            popUpTo(TopLevelDestination.CART.name)
                        }
                    )
                },
            )
        }

        CheckoutUiState.Navigate -> {
            appState.navigateToOrders(
                navOptions {
                    popUpTo(TopLevelDestination.CART.name)
                }
            )
        }
    }
}