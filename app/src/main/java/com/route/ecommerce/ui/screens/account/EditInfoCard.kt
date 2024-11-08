package com.route.ecommerce.ui.screens.account

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.route.ecommerce.R
import com.route.ecommerce.ui.components.EcomTextField

@Composable
fun EditInfoCard(
    onCancel: () -> Unit,
    token: String,
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
                text = stringResource(R.string.update_account_info),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
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
                value = viewModel.phoneValue,
                onValueChange = viewModel::updatePhone,
                labelId = R.string.phone_number,
                supportTextId =
                if (viewModel.isPhoneError) R.string.please_enter_a_valid_phone_number
                else R.string.phone_support_value,
                leadingIconId = R.drawable.ic_phone,
                trailingIconId =
                if (viewModel.phoneValue.isEmpty()) null
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
                    onClick = { viewModel.updateInfo(token) },
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