package com.route.ecommerce.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.auth.AuthUiEvents
import com.route.ecommerce.ui.components.EcomTextField

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
            trailingIconId =
            if (viewModel.emailValue.isEmpty()) null
            else R.drawable.ic_clear,
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

        Text(
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .clickable(onClick = appState::navigateToForgotPassword)
                .align(Alignment.End),
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(R.string.login),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.do_not_have_an_account),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable(
                    onClick = {
                        appState.popBackStack()
                        appState.navigateToSignup()
                    }
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        AuthUiEvents(
            authUiState = loginUiState,
            onSuccess = appState::navigateToTopLevelDestinations,
            onDismissRequest = viewModel::resetUiState,
        )
    }
}