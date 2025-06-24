package io.thomasgasangwa.bookcollection.presentation.navigation

import BookStoreTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.thomasgasangwa.bookcollection.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppNavigationScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    userIsAvailable: Boolean,
    signOut: () -> Unit
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
            if (userIsAvailable) {
                TooltipBox(
                    modifier = modifier,
                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                    tooltip = {
                        PlainTooltip { Text(text = stringResource(R.string.logout)) }
                    },
                    state = rememberTooltipState()
                ) {
                    IconButton(onClick = signOut) {
                        Icon(
                            painter = painterResource(R.drawable.logout_icon),
                            contentDescription = "logout"
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun AppBarPreview() {
    BookStoreTheme {
        AppBar(
            currentScreen = AppNavigationScreens.Tabs,
            canNavigateBack = false,
            navigateUp = {},
            signOut = {},
            userIsAvailable = false
        )
    }
}

@Preview
@Composable
private fun AppBarPreviewAddBook() {
    BookStoreTheme {
        AppBar(
            currentScreen = AppNavigationScreens.AddBook,
            canNavigateBack = true,
            navigateUp = {},
            signOut = {},
            userIsAvailable = false
        )
    }
}