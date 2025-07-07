package io.thomasgasangwa.bookcollection.presentation.navigation.app_bar

import BookStoreTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveModerator
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppNavigationScreens,
    currentUserIsLoggedIn: Boolean?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateToProfile: () -> Unit,
    signOut: () -> Unit
) {

    val appBarViewModel: AppBarViewModel = viewModel()
    val uiState by appBarViewModel.state.collectAsStateWithLifecycle()

    CenteredAppBar(
        currentScreen = currentScreen,
        currentUserIsLoggedIn = currentUserIsLoggedIn,
        canNavigateBack = canNavigateBack,
        navigateUp = navigateUp,
        navigateToProfile = navigateToProfile,
        signOut = signOut,
        onClickSettingsButton = { appBarViewModel.expandDropdown() },
        onClickDismissButton = { appBarViewModel.dismissDropdown() },
        uiState = uiState,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppNavigationScreens,
    currentUserIsLoggedIn: Boolean?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateToProfile: () -> Unit,
    signOut: () -> Unit,
    onClickSettingsButton: () -> Unit,
    onClickDismissButton: () -> Unit,
    uiState: DropDownUiState
) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(currentScreen.title))
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        modifier = Modifier,
                        contentDescription = stringResource(R.string.back_arrow)
                    )
                }
            }
        },
        actions = {
            if (currentUserIsLoggedIn != null && currentUserIsLoggedIn) {
                DropDownMenu(
                    navigateToProfile = navigateToProfile,
                    signOut = signOut,
                    onClickSettingsButton = onClickSettingsButton,
                    onClickDismissButton = onClickDismissButton,
                    uiState = uiState
                )
            }
        }
    )
}

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    navigateToProfile: () -> Unit,
    signOut: () -> Unit,
    onClickSettingsButton: () -> Unit,
    onClickDismissButton: () -> Unit,
    uiState: DropDownUiState
) {
    Box(
        modifier = modifier
            .padding(20.dp)
    ) {
        IconButton(onClick = onClickSettingsButton) {
            Icon(Icons.Default.Settings, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = uiState.isExpanded,
            onDismissRequest = onClickDismissButton
        ) {
            DropdownMenuItem(
                text = { Text("Profile") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "profile"
                    )
                },
                onClick = { navigateToProfile() }
            )
            DropdownMenuItem(
                text = { Text("LOG OUT") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.logout_icon),
                        contentDescription = "logout"
                    )
                },
                onClick = signOut
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "DELETE ACCOUNT",
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.RemoveModerator,
                        contentDescription = "delete account",
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = {/* Do something... */ }
            )
        }
    }
}

@Preview
@Composable
private fun CenteredAppBarPreview() {
    BookStoreTheme {
        CenteredAppBar(
            currentScreen = AppNavigationScreens.Tabs,
            currentUserIsLoggedIn = true,
            canNavigateBack = false,
            navigateUp = {},
            signOut = {},
            onClickSettingsButton = {},
            onClickDismissButton = {},
            uiState = DropDownUiState(),
            navigateToProfile = {}
        )
    }
}

@Preview
@Composable
private fun DropDownMenuPreview() {
    BookStoreTheme {
        DropDownMenu(
            signOut = {},
            onClickSettingsButton = {},
            onClickDismissButton = {},
            uiState = DropDownUiState(),
            navigateToProfile = {}
        )
    }
}