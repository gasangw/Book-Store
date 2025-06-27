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
import io.thomasgasangwa.bookcollection.presentation.book_list.BookListScreen
import io.thomasgasangwa.bookcollection.presentation.favorites.Favorites

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit
) {

    var state by rememberSaveable { mutableIntStateOf(0) }
    val titles = listOf("Bookings","Books", "Favorites")

    fun updateState(index: Int) {
        state = if (index == 0) 0 else if(index == 1) 1 else 2
    }

    TabsDisplay(
        state = state,
        titles = titles,
        onTabClicked = { it -> updateState(it) },
        onAddBookButtonClicked = onAddBookButtonClicked,
        onBookClicked = { it -> onBookClicked(it) },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabsDisplay(
    state: Int,
    titles: List<String>,
    onTabClicked: (Int) -> Unit,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current

    Column(modifier = modifier) {
        SecondaryTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = (state == index), onClick = { onTabClicked(index) },
                    text = { Text(text = title, color = MaterialTheme.colorScheme.onSurface) }
                )
            }
        }
        when (state) {
            0 -> if(isInPreview){
                FakeBookListScreen(
                    onAddBookButtonClicked = {},
                    onBookClicked = {}
                )
            } else{
                BookListScreen(
                    onAddBookButtonClicked = onAddBookButtonClicked,
                    onBookClicked = { id -> onBookClicked(id) }
                )
            }

            1 -> if(isInPreview) {
                FakeFavorites(
                onBookClicked = {}
                )
            } else {
                Favorites(
                    onBookClicked = { id -> onBookClicked(id) }
                )
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
    var state = 0
    var titles = listOf("Bookings","Books", "Favorites")
    BookStoreTheme {
        TabsDisplay(
            state = state,
            titles = titles,
            onTabClicked = { index -> },
            onAddBookButtonClicked = {},
            onBookClicked = { _ -> },
        )
    }
}