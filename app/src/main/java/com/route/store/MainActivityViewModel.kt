package com.route.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.datastore.UserData
import com.route.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userPreferencesRepository.userData.map {
        MainActivityUiState.Ready(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Ready(val userData: UserData) : MainActivityUiState

}