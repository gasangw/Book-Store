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
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.AuthViewModel
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInState
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigation
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens
import io.thomasgasangwa.bookcollection.presentation.navigation.app_bar.AppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookApp(modifier: Modifier = Modifier) {


    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.state.collectAsStateWithLifecycle()

    val currentUser = when (authState) {
        is SignInState.SignInUser -> (authState as SignInState.SignInUser).currentUser
        else -> null
    }

    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppNavigationScreens.valueOf(
        backStackEntry?.destination?.route?.split("/")[0] ?: AppNavigationScreens.Tabs.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                currentUser = currentUser,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
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
            currentUser = currentUser,
            modifier = modifier.padding(innerPadding)
        )
    }
}