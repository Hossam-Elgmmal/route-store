package com.route.ecommerce.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.ecommerce.R
import com.route.ecommerce.ui.theme.EcomTheme

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
            )
        },
        text = {
            Text(text = stringResource(id = textId))
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
private fun EcomErrorDialogPreview() {
    EcomTheme {
        EcomErrorDialog(
            iconId = R.drawable.ic_error,
            textId = R.string.connection_error,
            onDismissRequest = {}
        )
    }
}