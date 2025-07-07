package io.thomasgasangwa.bookcollection.presentation.tab

import BookStoreTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInUiState
import io.thomasgasangwa.bookcollection.presentation.book_list.BookListScreen
import io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.AllBookings
import io.thomasgasangwa.bookcollection.presentation.favorites.Favorites

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    state: SignInUiState,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit
) {

    var tabsState by rememberSaveable { mutableIntStateOf(0) }
    val userTitles = listOf("Books", "Favorites", "Bookings")
    val adminTitles = listOf("Books", "Bookings")


    fun updateState(index: Int) {
        tabsState = if (index == 0) 0 else if (index == 1) 1 else 2
    }

    TabsDisplay(
        state = state,
        tabsState = tabsState,
        userTitles = userTitles,
        adminTitles = adminTitles,
        onTabClicked = { it -> updateState(it) },
        onAddBookButtonClicked = onAddBookButtonClicked,
        onBookClicked = { it -> onBookClicked(it) },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabsDisplay(
    state: SignInUiState,
    tabsState: Int,
    userTitles: List<String>,
    adminTitles: List<String>,
    onTabClicked: (Int) -> Unit,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current

    Column(modifier = modifier) {

        if (state.user?.email.isNullOrEmpty()) {
            SecondaryTabRow(selectedTabIndex = tabsState) {
                userTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = (tabsState == index), onClick = { onTabClicked(index) },
                        text = { Text(text = title, color = MaterialTheme.colorScheme.onSurface) }
                    )
                }
            }

            when (tabsState) {
                0 -> if (isInPreview) {
                    FakeBookListScreen(
                        onAddBookButtonClicked = {},
                        onBookClicked = {}
                    )
                } else {
                    BookListScreen(
                        onAddBookButtonClicked = onAddBookButtonClicked,
                        onBookClicked = { id -> onBookClicked(id) }
                    )
                }

                1 -> if (isInPreview) {
                    FakeFavorites(
                        onBookClicked = {}
                    )
                } else {
                    Favorites(
                        onBookClicked = { id -> onBookClicked(id) }
                    )
                }

                2 -> {
                    AllBookings(
                        state = state
                    )
                }
            }
        } else {
            SecondaryTabRow(selectedTabIndex = tabsState) {
                adminTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = (tabsState == index), onClick = { onTabClicked(index) },
                        text = { Text(text = title, color = MaterialTheme.colorScheme.onSurface) }
                    )
                }
            }
            when (tabsState) {
                0 -> if (isInPreview) {
                    FakeBookListScreen(
                        onAddBookButtonClicked = {},
                        onBookClicked = {}
                    )
                } else {
                    BookListScreen(
                        onAddBookButtonClicked = onAddBookButtonClicked,
                        onBookClicked = { id -> onBookClicked(id) }
                    )
                }

                1 -> {
                    AllBookings(
                        state = state
                    )
                }
            }
        }
    }
}

@Composable
private fun FakeBookListScreen(
    modifier: Modifier = Modifier,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit,
) {
    Text(text = "This is a fake bookList screen")
}

@Composable
private fun FakeFavorites(
    modifier: Modifier = Modifier,
    onBookClicked: (Int) -> Unit
) {
    Text(text = "This is a fake favorite screen")
}

@Preview(showBackground = true)
@Composable
private fun TabsPreview() {
    var tabsState = 0
    var userTitles = listOf("Books", "Favorites", "Bookings")
    BookStoreTheme {
        TabsDisplay(
            state = SignInUiState(),
            tabsState = tabsState,
            adminTitles = userTitles,
            userTitles = userTitles,
            onTabClicked = { index -> },
            onAddBookButtonClicked = {},
            onBookClicked = { _ -> },
        )
    }
}