package io.thomasgasangwa.bookcollection.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.presentation.add_book.AddBook
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInScreen
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.SignUpScreen
import io.thomasgasangwa.bookcollection.presentation.book_details.BookDetails
import io.thomasgasangwa.bookcollection.presentation.profile.ProfileScreen
import io.thomasgasangwa.bookcollection.presentation.tab.Tabs
import io.thomasgasangwa.bookcollection.presentation.update_book.BookParcelableData
import io.thomasgasangwa.bookcollection.presentation.update_book.UpdateBook
import io.thomasgasangwa.bookcollection.presentation.view.LocalUserData

enum class AppNavigationScreens(@StringRes val title: Int) {
    SignIn(title = R.string.sign_in),
    SignUp(title = R.string.sign_up),
    Profile(title = R.string.profile),
    Tabs(title = R.string.app_name),
    AddBook(title = R.string.add_book),
    BookDetails(title = R.string.book_details),
    EditBook(title = R.string.edit_book)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentUserId = LocalUserData.current.user?.id ?: ""
    val currentUserIsLoggedIn = LocalUserData.current.isLoggedIn

    NavHost(
        navController = navController,
        startDestination = if (currentUserIsLoggedIn) AppNavigationScreens.Tabs.name else AppNavigationScreens.SignIn.name,
        modifier = modifier
    ) {
        composable(route = AppNavigationScreens.SignIn.name) {
            SignInScreen(
                modifier = modifier,
                onSignInNavigateToHomeScreen = {
                    navController.navigate(AppNavigationScreens.Tabs.name) {
                        popUpTo(AppNavigationScreens.SignIn.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSignUpButtonClick = { navController.navigate(AppNavigationScreens.SignUp.name) }
            )
        }

        composable(route = AppNavigationScreens.SignUp.name) {
            SignUpScreen(
                onSignUpNavigateToLogin = { navController.navigate(AppNavigationScreens.SignIn.name) }
            )
        }

        composable(route = AppNavigationScreens.Profile.name) {
            ProfileScreen()
        }

        composable(route = AppNavigationScreens.Tabs.name) {
            Tabs(
                onAddBookButtonClicked = { navController.navigate(AppNavigationScreens.AddBook.name) },
                onBookClicked = { id -> navController.navigate("${AppNavigationScreens.BookDetails.name}/$id") }
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
                userId = currentUserId,
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

