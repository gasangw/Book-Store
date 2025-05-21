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
import io.thomasgasangwa.bookstore.presentation.update_book.UpdateBook

enum class AppNavigationScreens(@StringRes val title: Int) {
    Tabs(title = R.string.app_name),
    AddBook(title = R.string.add_book),
    BookDetails(title = R.string.book_details),
    EditBook(title = R.string.edit_book)
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
                onCancel = {
                    navController.popBackStack(
                        AppNavigationScreens.Tabs.name,
                        inclusive = false
                    )
                },
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
            BookDetails( book -> {
            bookId = bookId,
            navController.currentBackStack?.savedStateHandle?.set("book", book),
            onEditBook = { navController.navigate(AppNavigationScreens.EditBook.name) }
        }


            )
        }

        composable(route = AppNavigationScreens.EditBook.name) {
            UpdateBook(
                onCancel = {
                    navController.popBackStack(
                        AppNavigationScreens.Tabs.name,
                        inclusive = false
                    )
                },
                modifier = Modifier
            )
        }
    }
}

