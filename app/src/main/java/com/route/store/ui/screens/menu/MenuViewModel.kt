package com.route.store.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.data.model.Category
import com.route.data.reposetory.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
) : ViewModel() {

    val menuUiState: StateFlow<MenuUiState> =
        categoryRepository.getCategories()
            .map { categoriesList ->
                if (categoriesList.isEmpty()) {
                    MenuUiState.Empty
                } else {
                    MenuUiState.Success(categoriesList)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MenuUiState.Loading
            )
}

sealed interface MenuUiState {
    data object Empty : MenuUiState
    data object Loading : MenuUiState
    data class Success(val categoriesList: List<Category>) : MenuUiState
}