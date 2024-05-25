package com.route.ecommerce.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.route.ecommerce.R
import com.route.ecommerce.ui.EcomAppState
import com.route.ecommerce.ui.screens.account.AccountScreen
import com.route.ecommerce.ui.screens.cart.CartScreen
import com.route.ecommerce.ui.screens.categories.CategoriesScreen
import com.route.ecommerce.ui.screens.home.HomeScreen
import com.route.ecommerce.ui.screens.login.LoginScreen
import com.route.ecommerce.ui.screens.productdetails.ProductDetailsScreen
import com.route.ecommerce.ui.screens.products.ProductsScreen
import com.route.ecommerce.ui.screens.search.SearchScreen
import com.route.ecommerce.ui.screens.signup.SignupScreen
import com.route.ecommerce.ui.screens.wishlist.WishlistScreen

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

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.LOGIN.name, navOptions)

fun NavController.navigateToSignup(navOptions: NavOptions? = null) =
    navigate(LowLevelDestination.SIGNUP.name, navOptions)


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
            navigateToProducts = appState::navigateToProducts,
            navigateToProductDetails = appState::navigateToProductDetails
        )
    }
}

fun NavGraphBuilder.categoriesScreen(
    appState: EcomAppState
) {
    composable(TopLevelDestination.CATEGORIES.name) {
        CategoriesScreen(
            navigateToProducts = appState::navigateToProducts
        )
    }
}

fun NavGraphBuilder.accountScreen(
    appState: EcomAppState
) {
    composable(TopLevelDestination.ACCOUNT.name) {
        AccountScreen(
            navigateToLogin = appState::navigateToLogin,
            navigateToSignup = appState::navigateToSignup,
            navigateToWishlist = appState::navigateToWishlist,
        )
    }
}

fun NavGraphBuilder.cartScreen(
    appState: EcomAppState
) {
    composable(TopLevelDestination.CART.name) {
        CartScreen(
            navigateToLogin = appState::navigateToLogin,
            navigateToProductDetails = appState::navigateToProductDetails
        )
    }
}

fun NavGraphBuilder.loginScreen(
    appState: EcomAppState
) {
    composable(route = LowLevelDestination.LOGIN.name) {
        LoginScreen(
            navigateToSignup = appState::navigateToSignup,
            navigateToAccount = appState::navigateToAccount
        )
    }
}

fun NavGraphBuilder.signupScreen(
    appState: EcomAppState
) {
    composable(route = LowLevelDestination.SIGNUP.name) {
        SignupScreen(
            navigateToAccount = appState::navigateToAccount
        )
    }
}

fun NavGraphBuilder.wishlistScreen(
    appState: EcomAppState
) {
    composable(LowLevelDestination.WISHLIST.name) {
        WishlistScreen(
            navigateToLogin = appState::navigateToLogin,
            navigateToProductDetails = appState::navigateToProductDetails
        )
    }
}

fun NavGraphBuilder.productDetailsScreen(
    appState: EcomAppState
) {
    composable(LowLevelDestination.PRODUCT_DETAILS.name) {
        ProductDetailsScreen(
            navigateToCart = appState::navigateToCart
        )
    }
}

fun NavGraphBuilder.productsScreen(
    appState: EcomAppState
) {
    composable(LowLevelDestination.PRODUCTS.name) {
        ProductsScreen(
            navigateToProductDetails = appState::navigateToProductDetails
        )
    }
}

fun NavGraphBuilder.searchScreen(
    appState: EcomAppState
) {
    composable(route = LowLevelDestination.SEARCH.name) {
        SearchScreen(
            canNavigateUp = appState.canNavigateUp,
            navigateUp = appState::navigateUp
        )
    }
}