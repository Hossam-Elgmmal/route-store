package com.route.ecommerce.ui.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.auth.AuthUiEvents
import com.route.ecommerce.ui.components.EcomTextField
import kotlinx.coroutines.CoroutineScope

@Composable
fun SignupScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {

    val signUpUiState by viewModel.signUpUiState.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.create_account),
            style = MaterialTheme.typography.displaySmall
        )
        EcomTextField(
            value = viewModel.nameValue,
            onValueChange = viewModel::updateName,
            labelId = R.string.username,
            supportTextId =
            if (viewModel.isNameError) R.string.sign_up_username_support_error
            else R.string.sign_up_username_support,
            leadingIconId = R.drawable.ic_name,
            trailingIconId =
            if (viewModel.nameValue.isEmpty()) null
            else R.drawable.ic_clear,
            trailingIconAction = viewModel::clearName,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visualTransformation = VisualTransformation.None,
            isError = viewModel.isNameError,
            modifier = Modifier.fillMaxWidth()
        )
        EcomTextField(
            value = viewModel.emailValue,
            onValueChange = viewModel::updateEmail,
            labelId = R.string.enter_email,
            supportTextId =
            if (viewModel.isEmailError) R.string.sign_up_email_support_error
            else R.string.sign_up_email_support,
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
            supportTextId = R.string.sign_up_password_support,
            leadingIconId = R.drawable.ic_password,
            trailingIconId =
            if (viewModel.isPasswordVisible) R.drawable.ic_visible
            else R.drawable.ic_not_visible,
            trailingIconAction = viewModel::togglePasswordVisibility,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if (viewModel.isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            isError = viewModel.isPasswordError,
            modifier = Modifier.fillMaxWidth()
        )
        EcomTextField(
            value = viewModel.rePasswordValue,
            onValueChange = viewModel::updateRePassword,
            labelId = R.string.confirm_password,
            supportTextId =
            if (viewModel.isRePasswordError) R.string.passwords_must_match
            else R.string.login_password_support,
            leadingIconId = R.drawable.ic_password,
            trailingIconId =
            if (viewModel.isRePasswordVisible) R.drawable.ic_visible
            else R.drawable.ic_not_visible,
            trailingIconAction = viewModel::toggleRePasswordVisibility,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if (viewModel.isRePasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            isError = viewModel.isRePasswordError,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = viewModel::signup,
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            contentPadding = PaddingValues(12.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable(
                    onClick = {
                        appState.popBackStack()
                        appState.navigateToLogin()
                    }
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        AuthUiEvents(
            authUiState = signUpUiState,
            onSuccess = appState::navigateToTopLevelDestinations,
            onDismissRequest = viewModel::resetUiState,
            coroutineScope = coroutineScope
        )
    }
}