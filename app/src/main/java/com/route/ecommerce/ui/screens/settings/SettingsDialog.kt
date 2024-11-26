package com.route.ecommerce.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.datastore.DarkTheme
import com.route.ecommerce.R

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        SettingsUiState.Loading -> {}
        is SettingsUiState.Ready -> {
            val readyUiState = uiState as SettingsUiState.Ready
            AlertDialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier = modifier.fillMaxWidth(0.85f),
                onDismissRequest = onDismiss,
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                confirmButton = {
                    Button(onClick = onDismiss) {
                        Text(text = stringResource(R.string.ok))
                    }
                },
                text = {
                    Column {
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.theme),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        DarkTheme.entries.forEach { theme ->
                            val themeText = when (theme) {
                                DarkTheme.FOLLOW_SYSTEM -> stringResource(R.string.system_default)
                                DarkTheme.LIGHT -> stringResource(R.string.light)
                                DarkTheme.DARK -> stringResource(R.string.dark)
                            }
                            TextButton(
                                onClick = { viewModel.setDarkTheme(theme) }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = theme == readyUiState.userData.darkTheme,
                                        onClick = { viewModel.setDarkTheme(theme) },
                                    )
                                    Text(text = themeText)
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            )
        }
    }
}