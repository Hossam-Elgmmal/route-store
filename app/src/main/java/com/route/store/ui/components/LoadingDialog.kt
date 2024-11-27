package com.route.store.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.route.store.ui.theme.EcomTheme


@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier
) {

    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = modifier,
            shape = CircleShape,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(8.dp)
            )
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