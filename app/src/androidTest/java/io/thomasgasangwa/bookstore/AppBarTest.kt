package io.thomasgasangwa.bookstore

import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.thomasgasangwa.bookcollection.presentation.navigation.AppNavigationScreens
import io.thomasgasangwa.bookcollection.presentation.navigation.app_bar.AppBar
import org.junit.Rule
import org.junit.Test

class AppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookStore_appBar() {
        val currentScreen = AppNavigationScreens.Tabs
        composeTestRule.setContent {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = false,
                navigateUp = {},
                signOut = {}
            )
        }
        composeTestRule.onNodeWithText("Book Store")
            .assertExists()
    }

    @Test
    fun addBook_appBar() {
        val currentScreen = AppNavigationScreens.AddBook
        composeTestRule.setContent {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = true,
                navigateUp = {},
                signOut = {}
            )
        }
        composeTestRule.onNodeWithText("Add Book")
            .assertExists()
        composeTestRule.onNode(
            hasAnyDescendant(
                hasContentDescription("Back arrow")
            )
        )
    }

    @Test
    fun detailsBook_appBar() {
        val currentScreen = AppNavigationScreens.BookDetails
        composeTestRule.setContent {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = true,
                navigateUp = {},
                signOut = {}
            )
        }
        composeTestRule.onNodeWithText("Details")
            .assertExists()
        composeTestRule.onNode(
            hasAnyDescendant(
                hasContentDescription("Back arrow")
            )
        )
    }

    @Test
    fun editBook_appBar() {
        val currentScreen = AppNavigationScreens.EditBook
        composeTestRule.setContent {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = true,
                navigateUp = {},
                signOut = {}
            )
        }
        composeTestRule.onNodeWithText("Edit")
            .assertExists()
        composeTestRule.onNode(
            hasAnyDescendant(
                hasContentDescription("Back arrow")
            )
        )
    }
}
