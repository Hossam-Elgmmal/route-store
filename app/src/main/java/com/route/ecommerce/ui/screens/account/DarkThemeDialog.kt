package com.route.ecommerce.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                DarkTheme.entries.forEach { theme ->
                    val themeText = when (theme) {
                        DarkTheme.FOLLOW_SYSTEM -> stringResource(R.string.follow_system)
                        DarkTheme.LIGHT -> stringResource(R.string.light_theme)
                        DarkTheme.DARK -> stringResource(R.string.dark_theme)
                    }
                    TextButton(
                        onClick = { setDarkTheme(theme) },
                    ) {
                        Text(text = themeText)
                        RadioButton(
                            selected = theme == selectedDarkTheme,
                            onClick = { setDarkTheme(theme) },
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

@Composable
fun DarkThemeButton(
    accountUiState: AccountUiState,
    setDarkTheme: (DarkTheme) -> Unit,
) {
    var showDarkThemeDialog by rememberSaveable { mutableStateOf(false) }
    TextButton(onClick = { showDarkThemeDialog = true }) {
        Text(text = stringResource(R.string.theme))
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_brightness),
            contentDescription = null
        )
    }

    if (accountUiState is AccountUiState.Ready && showDarkThemeDialog) {
        DarkThemeDialog(
            onDismissDialog = { showDarkThemeDialog = false },
            selectedDarkTheme = accountUiState.darkTheme,
            setDarkTheme = setDarkTheme,
        )
    }
}