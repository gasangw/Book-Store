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
import io.thomasgasangwa.bookcollection.common.state.UserState
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens
import io.thomasgasangwa.bookcollection.presentation.view.LocalUserData

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppNavigationScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    deleteAccount: () -> Unit,
    navigateToProfile: () -> Unit,
    signOut: () -> Unit
) {

    val appBarViewModel: AppBarViewModel = viewModel()
    val uiState by appBarViewModel.state.collectAsStateWithLifecycle()
    val currentUserInfo = LocalUserData.current

    CenteredAppBar(
        currentScreen = currentScreen,
        currentUserInfo = currentUserInfo,
        canNavigateBack = canNavigateBack,
        navigateUp = navigateUp,
        deleteAccount = deleteAccount,
        navigateToProfile = navigateToProfile,
        signOut = signOut,
        onClickSettingsButton = { appBarViewModel.expandDropdown() },
        onClickDismissButton = { appBarViewModel.dismissDropdown() },
        showDeleteDialog = {appBarViewModel.showDeleteDialog() },
        hideDeleteDialog = {appBarViewModel.hideDeleteDialog()},
        uiState = uiState,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppNavigationScreens,
    currentUserInfo: UserState,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    deleteAccount: () -> Unit,
    navigateToProfile: () -> Unit,
    signOut: () -> Unit,
    onClickSettingsButton: () -> Unit,
    onClickDismissButton: () -> Unit,
    showDeleteDialog: () -> Unit,
    hideDeleteDialog: () -> Unit,
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
            if (currentUserInfo.isLoggedIn && currentUserInfo.user?.id != null) {
                DropDownMenu(
                    navigateToProfile = navigateToProfile,
                    signOut = signOut,
                    deleteAccount = deleteAccount,
                    onClickSettingsButton = onClickSettingsButton,
                    onClickDismissButton = onClickDismissButton,
                    showDeleteDialog = showDeleteDialog,
                    hideDeleteDialog = hideDeleteDialog,
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
    deleteAccount: () -> Unit,
    onClickSettingsButton: () -> Unit,
    onClickDismissButton: () -> Unit,
    showDeleteDialog: () -> Unit,
    hideDeleteDialog: () -> Unit,
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
                onClick = { showDeleteDialog() }
            )
        }
    }
    if (uiState.showDeleteDialog){
        DeleteAccountDialog(
            onDismissRequest = hideDeleteDialog,
            onConfirmation = deleteAccount,
            dialogTitle = "Delete Account",
            dialogText = "Are you sure you want to delete your account?",
            icon = Icons.Default.RemoveModerator
        )
    }
}

@Preview
@Composable
private fun CenteredAppBarPreview() {
    BookStoreTheme {
        CenteredAppBar(
            currentScreen = AppNavigationScreens.Tabs,
            currentUserInfo = UserState(),
            canNavigateBack = false,
            navigateUp = {},
            signOut = {},
            deleteAccount = {},
            onClickSettingsButton = {},
            onClickDismissButton = {},
            showDeleteDialog = {},
            hideDeleteDialog = {},
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
            deleteAccount = {},
            onClickSettingsButton = {},
            onClickDismissButton = {},
            showDeleteDialog = {},
            hideDeleteDialog = {},
            uiState = DropDownUiState(),
            navigateToProfile = {}
        )
    }
}