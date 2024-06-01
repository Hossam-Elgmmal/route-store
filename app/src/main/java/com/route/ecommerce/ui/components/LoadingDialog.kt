package com.route.ecommerce.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.route.ecommerce.ui.theme.EcomTheme


@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Card(
            Modifier.padding(8.dp)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun LoadingDialogPreview() {
    EcomTheme {
        LoadingDialog()
    }
}