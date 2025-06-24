package io.thomasgasangwa.bookcollection.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.thomasgasangwa.bookcollection.presentation.auth.AuthViewModel
import io.thomasgasangwa.bookcollection.presentation.navigation.AppBar
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigation
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookApp(modifier: Modifier = Modifier) {


    val authViewModel: AuthViewModel = koinViewModel()

    val navController: NavHostController = rememberNavController()

    val userIsAvailable: Boolean = authViewModel.userExists

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppNavigationScreens.valueOf(
        backStackEntry?.destination?.route?.split("/")[0] ?: AppNavigationScreens.Tabs.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                userIsAvailable = userIsAvailable,
                signOut = {
                    authViewModel.signOut()
                    navController.navigate(AppNavigationScreens.SignIn.name) {
                        popUpTo(AppNavigationScreens.Tabs.name) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            userIsAvailable = userIsAvailable,
            modifier = modifier.padding(innerPadding)
        )
    }
}