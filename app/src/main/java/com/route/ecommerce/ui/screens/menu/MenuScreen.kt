package com.route.ecommerce.ui.screens.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.components.CategoryItem

@Composable
fun MenuScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val categoriesList by viewModel.categoriesList.collectAsState()
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier
    ) {
        if (categoriesList.isNotEmpty()) {
            LazyColumn {
                items(categoriesList) { category ->
                    CategoryItem(
                        category = category,
                        onCardClick = appState::navigateToProducts,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}