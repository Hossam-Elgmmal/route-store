package com.route.ecommerce.ui.auth.forgotpassword

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.auth.AuthUiEvents
import com.route.ecommerce.ui.components.EcomTextField
import com.route.ecommerce.ui.theme.successIconColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    appState: EcomAppState,
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.emailState.collectAsState()
    val isCodeSent by viewModel.isCodeSent.collectAsState()
    val hasVerifiedCode by viewModel.hasVerifiedCode.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!isCodeSent && !hasVerifiedCode) {

            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.will_send_you_a_verification_code),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            EcomTextField(
                value = email,
                onValueChange = viewModel::updateEmail,
                labelId = R.string.enter_email,
                supportTextId = if (viewModel.isEmailError) R.string.login_email_support_error
                else R.string.login_email_support,
                leadingIconId = R.drawable.ic_email,
                trailingIconId = if (email.isEmpty()) null
                else R.drawable.ic_clear,
                trailingIconAction = viewModel::clearEmail,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                visualTransformation = VisualTransformation.None,
                isError = viewModel.isEmailError,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = { viewModel.sendVerificationCode(email) },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.send_code),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
            }

        } else if (isCodeSent && !hasVerifiedCode) {
            CodeSuccessDialog(
                msgId = R.string.verification_code_sent_to_your_email
            )

            Text(
                text = stringResource(R.string.check_your_email),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            val code = remember { mutableStateListOf(*Array(6) { "" }) }
            val focusRequesters = remember { mutableStateListOf(*Array(6) { FocusRequester() }) }
            val focusManager = LocalFocusManager.current

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.enter_the_code_sent_to))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(email)
                    }
                },
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                for (i in 0 until 6) {
                    OutlinedTextField(
                        value = code[i],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                code[i] = newValue

                                if (newValue.isNotEmpty()) {
                                    if (i < 5) {
                                        focusRequesters[i + 1].requestFocus()
                                    } else {
                                        focusManager.clearFocus()
                                        viewModel.submitCode(code.joinToString(separator = ""))
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .width(46.dp)
                            .focusRequester(focusRequesters[i])
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Backspace) {
                                    if (code[i].isNotEmpty()) {
                                        code[i] = ""
                                    } else if (code[i].isEmpty() && i > 0) {
                                        code[i - 1] = ""
                                        focusRequesters[i - 1].requestFocus()
                                    }
                                    true
                                } else {
                                    false
                                }
                            },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        supportingText = {},
                    )
                    if (i == 2) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            Button(
                onClick = { viewModel.submitCode(code.joinToString(separator = "")) },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
            }

        } else {

            CodeSuccessDialog(
                msgId = R.string.verified_code_successfully
            )
            Text(
                text = stringResource(R.string.reset_password),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.enter_your_new_password),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
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
                onClick = { viewModel.resetPassword(email) },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.reset_password),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        AuthUiEvents(
            authUiState = uiState,
            onSuccess = appState::navigateToTopLevelDestinations,
            onDismissRequest = viewModel::resetUiState,
            coroutineScope = coroutineScope
        )
    }
}

@Composable
fun CodeSuccessDialog(
    @StringRes msgId: Int,
    modifier: Modifier = Modifier,
) {
    var showDialog by rememberSaveable { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_success),
                    contentDescription = null,
                    tint = successIconColor
                )
            },
            text = {
                Text(stringResource(msgId))
            }
        )
    }
    LaunchedEffect(key1 = Unit) {
        delay(2_000)
        showDialog = false
    }
}