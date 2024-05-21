package com.route.ecommerce.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    val accountUiState: StateFlow<AccountUiState> =
        userDataRepository.userData
            .map {
                AccountUiState.Ready(
                    darkTheme = it.darkTheme
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AccountUiState.Loading
            )

    fun toggleDarkTheme(isChecked: Boolean) {
        if (accountUiState.value is AccountUiState.Ready)
            viewModelScope.launch {
                userDataRepository.saveThemePreferences(isChecked)
            }
    }
}

sealed interface AccountUiState {
    data object Loading : AccountUiState
    data class Ready(val darkTheme: Boolean) : AccountUiState
}