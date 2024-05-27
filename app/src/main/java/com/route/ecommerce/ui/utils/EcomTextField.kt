package com.route.ecommerce.ui.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun EcomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelId: Int,
    @StringRes supportTextId: Int,
    @DrawableRes leadingIconId: Int,
    @DrawableRes trailingIconId: Int,
    trailingIconAction: () -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        label = { Text(stringResource(labelId)) },
        supportingText = { Text(stringResource(supportTextId)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIconId),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = trailingIconAction) {
                Icon(
                    painter = painterResource(id = trailingIconId),
                    contentDescription = null
                )
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = modifier
    )
}