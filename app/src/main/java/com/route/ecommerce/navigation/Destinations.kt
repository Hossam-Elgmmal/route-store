package com.route.ecommerce.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.screens.CartScreen
import com.route.ecommerce.ui.screens.CategoriesScreen
import com.route.ecommerce.ui.screens.ProductDetailsScreen
import com.route.ecommerce.ui.screens.ProductsScreen
import com.route.ecommerce.ui.screens.SearchScreen
import com.route.ecommerce.ui.screens.WishlistScreen
import com.route.ecommerce.ui.screens.account.AccountScreen
import com.route.ecommerce.ui.screens.home.HomeScreen

enum class TopLevelDestination(
    @DrawableRes val iconId: Int,
    @DrawableRes val selectedIconId: Int,
    @StringRes val iconTextId: Int,
) {
    HOME(
        iconId = R.drawable.ic_home,
        selectedIconId = R.drawable.ic_selected_home,
        iconTextId = R.string.home,
    ),
    CATEGORIES(
        iconId = R.drawable.ic_categories,
        selectedIconId = R.drawable.ic_selected_categories,
        iconTextId = R.string.categories,
    ),
    CART(
        iconId = R.drawable.ic_cart,
        selectedIconId = R.drawable.ic_selected_cart,
        iconTextId = R.string.cart,
    ),
    ACCOUNT(
        iconId = R.drawable.ic_account,
        selectedIconId = R.drawable.ic_selected_account,
        iconTextId = R.string.account,
    ),
}

enum class LowLevelDestination {
    LOGIN,
    SIGNUP,
    WISHLIST,
    PRODUCTS,
    PRODUCT_DETAILS,
    SEARCH,
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.HOME.name, navOptions)

fun NavController.navigateToCategories(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.CATEGORIES.name, navOptions)

fun NavController.navigateToAccount(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.ACCOUNT.name, navOptions)

fun NavController.navigateToWishlist(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.WISHLIST.name, navOptions)


fun NavController.navigateToCart(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.CART.name, navOptions)


fun NavController.navigateToProductDetails(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.PRODUCT_DETAILS.name, navOptions)

fun NavController.navigateToProducts(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.PRODUCTS.name, navOptions)


fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.SEARCH.name, navOptions)

fun NavGraphBuilder.homeScreen(
    appState: EcomAppState
) {
    composable(route = TopLevelDestination.HOME.name) {
        HomeScreen(
            appState = appState
        )
    }
}
fun NavGraphBuilder.categoriesScreen() {
    composable(TopLevelDestination.CATEGORIES.name) {
        CategoriesScreen()
    }
}

fun NavGraphBuilder.accountScreen(
    appState: EcomAppState
) {
    composable(TopLevelDestination.ACCOUNT.name) {
        AccountScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.cartScreen() {
    composable(TopLevelDestination.CART.name) {
        CartScreen()
    }
}

fun NavGraphBuilder.wishlistScreen() {
    composable(LowLevelDestination.WISHLIST.name) {
        WishlistScreen()
    }
}


fun NavGraphBuilder.productDetailsScreen() {
    composable(LowLevelDestination.PRODUCT_DETAILS.name) {
        ProductDetailsScreen()
    }
}

fun NavGraphBuilder.productsScreen() {
    composable(LowLevelDestination.PRODUCTS.name) {
        ProductsScreen()
    }
}

fun NavGraphBuilder.searchScreen() {
    composable(route = LowLevelDestination.SEARCH.name) {
        SearchScreen()
    }
}