package com.route.store.ui.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.route.store.R
import com.route.store.ui.components.EcomTextField

@Composable
fun EditPasswordCard(
    onCancel: () -> Unit,
    token: String,
    password: String,
    viewModel: AccountViewModel,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.update_password),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            EcomTextField(
                value = viewModel.passwordValue,
                onValueChange = viewModel::updatePassword,
                labelId = R.string.current_password,
                supportTextId =
                if (viewModel.isPasswordError) R.string.wrong_password
                else R.string.empty,
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
                value = viewModel.newPasswordValue,
                onValueChange = viewModel::updateNewPassword,
                labelId = R.string.new_password,
                supportTextId = R.string.sign_up_password_support,
                leadingIconId = R.drawable.ic_password,
                trailingIconId =
                if (viewModel.isNewPasswordVisible) R.drawable.ic_visible
                else R.drawable.ic_not_visible,
                trailingIconAction = viewModel::toggleNewPasswordVisibility,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (viewModel.isNewPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                isError = viewModel.isNewPasswordError,
                modifier = Modifier.fillMaxWidth()
            )
            EcomTextField(
                value = viewModel.rePasswordValue,
                onValueChange = viewModel::updateRePassword,
                labelId = R.string.confirm_password,
                supportTextId =
                if (viewModel.isRePasswordError) R.string.passwords_must_match
                else R.string.empty,
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
            Row {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                    )
                }
                Button(
                    onClick = { viewModel.changePassword(token, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    Text(
                        text = stringResource(R.string.update),
                    )
                }
            }
        }
    }
}