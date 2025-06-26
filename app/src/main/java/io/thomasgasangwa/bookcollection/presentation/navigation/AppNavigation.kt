package io.thomasgasangwa.bookcollection.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.presentation.add_book.AddBook
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInScreen
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.SignUpScreen
import io.thomasgasangwa.bookcollection.presentation.book_details.BookDetails
import io.thomasgasangwa.bookcollection.presentation.tab.Tabs
import io.thomasgasangwa.bookcollection.presentation.update_book.BookParcelableData
import io.thomasgasangwa.bookcollection.presentation.update_book.UpdateBook

enum class AppNavigationScreens(@StringRes val title: Int) {
    SignIn(title = R.string.sign_in),
    SignUp(title = R.string.sign_up),
    Tabs(title = R.string.app_name),
    AddBook(title = R.string.add_book),
    BookDetails(title = R.string.book_details),
    EditBook(title = R.string.edit_book)
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    currentUser: User?,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = if(currentUser != null)  AppNavigationScreens.Tabs.name else  AppNavigationScreens.SignIn.name ,
        modifier = modifier
    ) {
        composable(route = AppNavigationScreens.SignIn.name) {
            SignInScreen(
                modifier = modifier,
                onSignInSuccess = {
                    navController.navigate(AppNavigationScreens.Tabs.name) {
                        popUpTo(AppNavigationScreens.SignIn.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSignUpButtonClick = { navController.navigate(AppNavigationScreens.SignUp.name)}
            )
        }

        composable(route = AppNavigationScreens.SignUp.name) {
            SignUpScreen()
        }

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
                onAddBook = {
                    navController.popBackStack(
                        AppNavigationScreens.Tabs.name,
                        inclusive = false
                    )
                },
            )
        }
        composable(
            route = "${AppNavigationScreens.BookDetails.name}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("id")
            BookDetails(
                bookId = bookId,
                onEditBook = { book: BookParcelableData ->
                    navController.currentBackStackEntry?.savedStateHandle?.set<BookParcelableData>(
                        "book",
                        book
                    )
                    navController.navigate(AppNavigationScreens.EditBook.name)
                },
            )
        }

        composable(route = AppNavigationScreens.EditBook.name) {
            val book =
                navController.previousBackStackEntry?.savedStateHandle?.get<BookParcelableData>("book")
            if (book != null) {
                UpdateBook(
                    onCancel = {
                        navController.popBackStack(
                            AppNavigationScreens.Tabs.name,
                            inclusive = false
                        )
                    },
                    onUpdateBook = {
                        navController.popBackStack(
                            AppNavigationScreens.Tabs.name,
                            inclusive = false
                        )
                    },
                    book = book,
                    modifier = Modifier
                )
            }
        }
    }
}

