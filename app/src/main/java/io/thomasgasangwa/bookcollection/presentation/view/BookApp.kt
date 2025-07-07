package io.thomasgasangwa.bookcollection.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInViewModel
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigation
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens
import io.thomasgasangwa.bookcollection.presentation.navigation.app_bar.AppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookApp(modifier: Modifier = Modifier) {

    val authViewModel: SignInViewModel = koinViewModel()

    val state by authViewModel.state.collectAsStateWithLifecycle()

    val currentUserIsLoggedIn by authViewModel.currentUserIsLoggedIn.collectAsStateWithLifecycle()

    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppNavigationScreens.valueOf(
        backStackEntry?.destination?.route?.split("/")[0] ?: AppNavigationScreens.Tabs.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                currentUserIsLoggedIn = currentUserIsLoggedIn,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navigateToProfile = {
                    navController.navigate(AppNavigationScreens.Profile.name)
                },
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
            state = state,
            currentUserIsLoggedIn = currentUserIsLoggedIn,
            modifier = modifier.padding(innerPadding)
        )
    }
}