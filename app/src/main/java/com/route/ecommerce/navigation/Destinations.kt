package com.route.ecommerce.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.screens.CheckoutScreen
import com.route.ecommerce.ui.screens.ProductDetailsScreen
import com.route.ecommerce.ui.screens.ProductsScreen
import com.route.ecommerce.ui.screens.SearchScreen
import com.route.ecommerce.ui.screens.WishlistScreen
import com.route.ecommerce.ui.screens.account.AccountScreen
import com.route.ecommerce.ui.screens.cart.CartScreen
import com.route.ecommerce.ui.screens.home.HomeScreen
import com.route.ecommerce.ui.screens.menu.MenuScreen

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
    MENU(
        iconId = R.drawable.ic_menu,
        selectedIconId = R.drawable.ic_selected_menu,
        iconTextId = R.string.menu,
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
    WISHLIST,
    PRODUCTS,
    PRODUCT_DETAILS,
    SEARCH,
    CHECKOUT,
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.HOME.name, navOptions)

fun NavController.navigateToMenu(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.MENU.name, navOptions)

fun NavController.navigateToAccount(navOptions: NavOptions? = null) =
    navigate(TopLevelDestination.ACCOUNT.name, navOptions)

fun NavController.navigateToWishlist(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.WISHLIST.name, navOptions)

fun NavController.navigateToCheckout(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.CHECKOUT.name, navOptions)


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

fun NavGraphBuilder.menuScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit
) {
    composable(TopLevelDestination.MENU.name) {
        MenuScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
    }
}

fun NavGraphBuilder.accountScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit
) {
    composable(TopLevelDestination.ACCOUNT.name) {
        AccountScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
    }
}

fun NavGraphBuilder.cartScreen(
    appState: EcomAppState,
    onBackPressed: () -> Unit
) {
    composable(TopLevelDestination.CART.name) {
        CartScreen(
            appState = appState,
            onBackPressed = onBackPressed
        )
    }
}

fun NavGraphBuilder.wishlistScreen(
    appState: EcomAppState
) {
    composable(LowLevelDestination.WISHLIST.name) {
        WishlistScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.productDetailsScreen(appState: EcomAppState) {
    composable(LowLevelDestination.PRODUCT_DETAILS.name) {
        ProductDetailsScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.productsScreen(appState: EcomAppState) {
    composable(LowLevelDestination.PRODUCTS.name) {
        ProductsScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.searchScreen(appState: EcomAppState) {
    composable(route = LowLevelDestination.SEARCH.name) {
        SearchScreen(
            appState = appState
        )
    }
}

fun NavGraphBuilder.checkoutScreen(appState: EcomAppState) {
    composable(LowLevelDestination.CHECKOUT.name) {
        CheckoutScreen(
            appState = appState
        )
    }
}