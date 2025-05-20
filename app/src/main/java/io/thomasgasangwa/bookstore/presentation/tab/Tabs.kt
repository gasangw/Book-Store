package io.thomasgasangwa.bookstore.presentation.tab

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
import androidx.compose.ui.tooling.preview.Preview
import io.thomasgasangwa.bookstore.presentation.book_list.BookListScreen
import io.thomasgasangwa.bookstore.presentation.favorites.Favorites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit
) {

    var state by rememberSaveable { mutableIntStateOf(0) }
    val titles = listOf("Books", "Favorites")

    Column(modifier = modifier) {
        SecondaryTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = (state == index), onClick = { state = if (index == 0) 0 else 1 },
                    text = { Text(text = title, color = MaterialTheme.colorScheme.onSurface) }
                )
            }
        }
        when (state) {
            0 -> BookListScreen(
                onAddBookButtonClicked = onAddBookButtonClicked,
                onBookClicked = { id -> onBookClicked(id) }
            )

            1 -> Favorites(
                onBookClicked = { id -> onBookClicked(id) }
            )
        }
    }
}

@Preview
@Composable
private fun TabsPreview() {
    BookStoreTheme {
//        Tabs()
    }
}