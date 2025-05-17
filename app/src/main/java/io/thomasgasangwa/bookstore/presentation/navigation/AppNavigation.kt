package io.thomasgasangwa.bookstore.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.add_book.AddBook
import io.thomasgasangwa.bookstore.presentation.tab.Tabs

enum class AppNavigationScreens(@StringRes val title: Int) {
    Tabs(title = R.string.app_name),
    AddBook(title = R.string.add_book)
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = AppNavigationScreens.Tabs.name,
        modifier = modifier
    ) {
        composable(route = AppNavigationScreens.Tabs.name) {
            Tabs(
                onAddBookButtonClicked = { navController.navigate(AppNavigationScreens.AddBook.name) }
            )
        }
        composable(route = AppNavigationScreens.AddBook.name) {
            AddBook(
                onAddBook = {},
                onCancel = {},
                modifier = Modifier
            )
        }
    }
}

