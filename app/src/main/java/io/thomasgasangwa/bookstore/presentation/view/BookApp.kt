package io.thomasgasangwa.bookstore.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.thomasgasangwa.bookstore.presentation.navigation.AppBar
import io.thomasgasangwa.bookstore.presentation.navigation.AppNavigation
import io.thomasgasangwa.bookstore.presentation.navigation.AppNavigationScreens

@Composable
fun BookApp(modifier: Modifier = Modifier) {

    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppNavigationScreens.valueOf(
        backStackEntry?.destination?.route ?: AppNavigationScreens.Tabs.name
    )

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            modifier = modifier.padding(innerPadding)
        )
    }
}