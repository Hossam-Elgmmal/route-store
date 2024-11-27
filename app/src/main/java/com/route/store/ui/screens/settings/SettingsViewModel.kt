package com.route.store.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.datastore.DarkTheme
import com.route.datastore.UserData
import com.route.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = userPreferencesRepository.userData.map {
        SettingsUiState.Ready(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SettingsUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun setDarkTheme(darkTheme: DarkTheme) {
        if (uiState.value is SettingsUiState.Ready)
            viewModelScope.launch {
                userPreferencesRepository.setDarkTheme(darkTheme)
            }
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Ready(val userData: UserData) : SettingsUiState
}
