package com.route.ecommerce.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.ecommerce.R
import com.route.ecommerce.ui.theme.EcomTheme
import com.route.ecommerce.ui.theme.successIconColor

@Composable
fun EcomErrorDialog(
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
                tint = if (iconId == R.drawable.ic_success) successIconColor
                else MaterialTheme.colorScheme.error
            )
        },
        title = {
            if (textId == R.string.success) {
                Text(text = stringResource(id = textId))
            }
        },
        text = {
            if (textId != R.string.success) {
                Text(text = stringResource(id = textId))
            }
        },
        confirmButton = {
            if (textId != R.string.success) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(R.string.dismiss))
                }
            }
        }
    )
}

@Preview
@Composable
private fun EcomErrorDialogPreview() {
    EcomTheme(
        darkTheme = true
    ) {
        EcomErrorDialog(
            iconId = R.drawable.ic_error,
            textId = R.string.connection_error,
            onDismissRequest = {}
        )
    }
}

@Preview
@Composable
private fun EcomSuccessDialogPreview() {
    EcomTheme(
        darkTheme = true
    ) {
        EcomErrorDialog(
            iconId = R.drawable.ic_success,
            textId = R.string.success,
            onDismissRequest = {}
        )
    }
}