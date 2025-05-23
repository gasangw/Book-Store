package io.thomasgasangwa.bookstore.presentation.update_book

import BookStoreTheme
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.presentation.view.components.TextFieldElement
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UpdateBook(
    book: BookParcelableData,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onUpdateBook: () -> Unit
) {
    val originalBookType = book.toBook()
    val updateBookViewModel: UpdateBookViewModel =
        koinViewModel(parameters = { parametersOf(originalBookType) })
    val updateBookState by updateBookViewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    UpdateBookDisplay(
        updateBookState = updateBookState,
        onUpdateBook = onUpdateBook,
        onCancel = onCancel,
        context = context,
        updateTitle = {it -> updateBookViewModel.updateTitle(it)},
        updateDescription = {it -> updateBookViewModel.updateDescription(it)},
        updateReleaseDate = {it -> updateBookViewModel.updateReleaseDate(it)},
        updatePages = {it -> updateBookViewModel.updatePages(it)},
        updateCover = {it -> updateBookViewModel.updateCover(it)},
        updateLikes = {it  -> updateBookViewModel.updateLikes(it)},
        updateBook = {updateBookViewModel.updateBook()},
        modifier = modifier
    )

}

@Composable
fun UpdateBookDisplay(
    updateBookState: UpdateBookState,
    onUpdateBook: () -> Unit,
    onCancel: () -> Unit,
    context: Context,
    updateTitle: (String) -> Unit,
    updateDescription: (String) -> Unit,
    updateReleaseDate: (String) -> Unit,
    updatePages: (String) -> Unit,
    updateCover: (String) -> Unit,
    updateLikes: (String) -> Unit,
    updateBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when (updateBookState) {
            is UpdateBookState.Success -> {
                val updateBook = updateBookState.book
                Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    TextFieldElement(
                        textValue = updateBook.title,
                        label = "Title",
                        onValueChange = {
                            updateTitle(it) },
                        textFieldHasError = updateBook.title.isEmpty(),
                        singleLine = true,
                        placeholder = "e.g Rich Dad Poor Dad",
                        textErrorMessage = "Title cannot be empty.."
                    )
                    TextFieldElement(
                        textValue = updateBook.description,
                        label = "description",
                        onValueChange = { updateDescription(it) },
                        textFieldHasError = updateBook.description.isEmpty(),
                        singleLine = true,
                        placeholder = "e.g this book is about money",
                        textErrorMessage = "description cannot be empty.."
                    )
                    TextFieldElement(
                        textValue = updateBook.releaseDate,
                        label = "Release Date",
                        onValueChange = { updateReleaseDate(it) },
                        singleLine = false,
                        placeholder = "July 23, 2020",
                        textErrorMessage = "Release Date cannot be empty..",
                        textFieldHasError = updateBook.releaseDate.isEmpty()

                    )
                    TextFieldElement(
                        textValue = updateBook.pages.toString(), label = "Pages",
                        onValueChange = { updatePages(it) },
                        textFieldHasError = updateBook.pages <= 0,
                        singleLine = true,
                        placeholder = "e.g 200",
                        textErrorMessage = "Pages cannot be empty.."
                    )
                    TextFieldElement(
                        textValue = updateBook.cover,
                        label = "Cover",
                        onValueChange = { updateCover(it) },
                        textFieldHasError = false,
                        singleLine = true,
                        placeholder = "e.g https://picsum.photos/200",
                        textErrorMessage = ""
                    )
                    TextFieldElement(
                        textValue = updateBook.likes.toString(), label = "Likes",
                        onValueChange = { updateLikes(it) },
                        textFieldHasError = false,
                        singleLine = true,
                        placeholder = "e.g 100",
                        textErrorMessage = ""
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

                Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = onCancel,
                        modifier = modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            updateBook()
                            Toast.makeText(context, "Book updated successfully", Toast.LENGTH_SHORT)
                                .show()
                            onUpdateBook()
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text(text = "Update")
                    }
                }
            }

            is UpdateBookState.Error -> {
                val error = updateBookState.exception
                Text(
                    text = "Error $error occurred while updating the books",
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.error)
                )
            }
        }
    }
}


@Preview
@Composable
private fun UpdateBookPreview() {
    val context = LocalContext.current
    val sampleBook = BookParcelableData(
        id = 1,
        title = "Sample Book",
        cover = "sample_cover_url",
        pages = 300,
        releaseDate = "2023-01-01",
        description = "Sample description",
        likes = 100,
        isFavorite = false
    )
    BookStoreTheme {
        UpdateBookDisplay(
            onCancel = {},
            onUpdateBook = {},
            updateBookState = UpdateBookState.Success(sampleBook.toBook()),
        context = context,
        updateTitle = {_, ->},
        updateDescription = {_, ->},
        updateReleaseDate = {_, ->},
        updatePages = {_, ->},
        updateCover = {_, ->},
        updateLikes = {_, ->},
        updateBook = {},
        modifier = Modifier
        )

    }
}
