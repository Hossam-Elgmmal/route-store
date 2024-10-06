package com.route.ecommerce.ui.screens.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val shape = RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp)
    if (categoriesList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categoriesList) { category ->
                CategoryItem(
                    category = category,
                    onCardClick = appState::navigateToProducts,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = shape
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}