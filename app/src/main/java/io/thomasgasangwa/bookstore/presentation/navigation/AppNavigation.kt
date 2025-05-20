package io.thomasgasangwa.bookstore.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.add_book.AddBook
import io.thomasgasangwa.bookstore.presentation.book_details.BookDetails
import io.thomasgasangwa.bookstore.presentation.tab.Tabs
import timber.log.Timber

enum class AppNavigationScreens(@StringRes val title: Int) {
    Tabs(title = R.string.app_name),
    AddBook(title = R.string.add_book),
    BookDetails(title = R.string.book_details)
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
                onAddBookButtonClicked = { navController.navigate(AppNavigationScreens.AddBook.name) },
                onBookClicked = { id -> navController.navigate("${AppNavigationScreens.BookDetails.name}/$id") },
            )
        }
        composable(route = AppNavigationScreens.AddBook.name) {
            AddBook(
                onCancel = {},
                modifier = Modifier
            )
        }
        composable(
            route = "${AppNavigationScreens.BookDetails.name}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("id")
            Timber.d("Book id $bookId")
            BookDetails(bookId = bookId)
        }
    }
}

