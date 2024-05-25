package com.route.ecommerce.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.route.ecommerce.R
import com.route.model.DarkTheme

@Composable
fun DarkThemeDialog(
    onDismissDialog: () -> Unit,
    selectedDarkTheme: DarkTheme,
    setDarkTheme: (DarkTheme) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissDialog,
        confirmButton = {
            Button(onClick = onDismissDialog) {
                Text(text = stringResource(R.string.ok))
            }
        },
        text = {
            Column {
                DarkTheme.entries.forEach {
                    TextButton(
                        onClick = { setDarkTheme(it) },
                    ) {
                        Text(text = it.name)
                        RadioButton(
                            selected = it == selectedDarkTheme,
                            onClick = { setDarkTheme(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                }
            }
        }
    )
}