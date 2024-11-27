package com.route.store.ui.screens.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.route.store.R
import com.route.store.ui.EcomAppState
import com.route.store.ui.components.CategoryItem

@Composable
fun MenuScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val menuUiState by viewModel.menuUiState.collectAsState()
    BackHandler {
        onBackPressed()
    }
    val shape = RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp)
    when (menuUiState) {
        MenuUiState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.img_no_connection_bro),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

        MenuUiState.Loading -> {}
        is MenuUiState.Success -> {
            val successUiState = menuUiState as MenuUiState.Success
            LazyVerticalGrid(
                columns = GridCells.Adaptive(320.dp),
                modifier = modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(successUiState.categoriesList) { category ->
                    CategoryItem(
                        category = category,
                        onCardClick = appState::navigateToProducts,
                        shape = shape
                    )
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}