package com.route.ecommerce.ui.auth.login

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.navigation.TopLevelDestination
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.utils.EcomTextField

@Composable
fun LoginScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val loginUiState by viewModel.loginUiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.displaySmall
        )
        EcomTextField(
            value = viewModel.emailValue,
            onValueChange = viewModel::updateEmail,
            labelId = R.string.enter_email,
            supportTextId =
            if (viewModel.isEmailError) R.string.login_email_support_error
            else R.string.login_email_support,
            leadingIconId = R.drawable.ic_email,
            trailingIconId = R.drawable.ic_clear,
            trailingIconAction = viewModel::clearEmail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            visualTransformation = VisualTransformation.None,
            isError = viewModel.isEmailError,
            modifier = Modifier.fillMaxWidth()
        )
        EcomTextField(
            value = viewModel.passwordValue,
            onValueChange = viewModel::updatePassword,
            labelId = R.string.enter_password,
            supportTextId =
            if (viewModel.isPasswordError) R.string.login_password_support_error
            else R.string.login_password_support,
            leadingIconId = R.drawable.ic_password,
            trailingIconId =
            if (viewModel.isPasswordVisible) R.drawable.ic_visible
            else R.drawable.ic_not_visible,
            trailingIconAction = viewModel::togglePasswordVisibility,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if (viewModel.isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            isError = viewModel.isPasswordError,
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            onClick = appState::navigateToForgotPassword,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(R.string.forgot_password))
        }

        Button(
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        TextButton(
            onClick = appState::navigateToSignup,
            modifier = Modifier.align(Alignment.End)

        ) {
            Text(text = stringResource(R.string.or_create_a_new_account))
        }
        LoginUiEvents(
            loginUiState = loginUiState,
            onSuccess = appState::navigateToTopLevelDestinations,
            onDismissRequest = viewModel::resetUiState,
        )
    }
}

@Composable
fun LoginUiEvents(
    loginUiState: LoginUiState,
    onSuccess: (
        topLevelDestination: TopLevelDestination,
        sameTopLevelDestination: Boolean
    ) -> Unit,
    onDismissRequest: () -> Unit,
) {
    when (loginUiState) {
        LoginUiState.Idle -> {}
        is LoginUiState.Error -> {
            ErrorDialog(
                iconId = R.drawable.ic_error,
                textId = loginUiState.uiError.errorMessageId,
                onDismissRequest = onDismissRequest
            )
        }

        LoginUiState.Loading -> {
            LoadingDialog()
        }

        is LoginUiState.Success -> {
            onSuccess(
                TopLevelDestination.ACCOUNT,
                true
            )
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Card {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorDialog(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
            )
        },
        text = {
            Text(text = stringResource(id = textId))
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.ok))
            }
        }
    )
}