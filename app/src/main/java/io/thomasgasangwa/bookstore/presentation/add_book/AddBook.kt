package io.thomasgasangwa.bookstore.presentation.add_book

import BookStoreTheme
import android.content.Context
import android.widget.Toast
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

@Composable
fun AddBook(
    onCancel: () -> Unit,
    onAddBook: () -> Unit
) {

    val addBookViewModel: AddBookViewModel = koinViewModel()
    val formState by addBookViewModel.formState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    AddBookForm(
        onCancel = onCancel,
        onAddBook = onAddBook,
        formState = formState,
        context = context,
        onTitleChanged = { it -> addBookViewModel.onTitleChanged(it) },
        onDescriptionChanged = { it -> addBookViewModel.onDescriptionChanged(it) },
        onReleaseDateChanged = { it -> addBookViewModel.onReleaseDateChanged(it) },
        onPagesChanged = { it -> addBookViewModel.onPagesChanged(it) },
        onCoverChanged = { it -> addBookViewModel.onCoverChanged(it) },
        onLikesChanged = { it -> addBookViewModel.onLikesChanged(it) },
        addBook = { addBookViewModel.addBook() },
    )

}

@Composable
fun AddBookForm(
    onCancel: () -> Unit,
    onAddBook: () -> Unit,
    formState: AddBookFormState,
    context: Context,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onReleaseDateChanged: (String) -> Unit,
    onPagesChanged: (String) -> Unit,
    onCoverChanged: (String) -> Unit,
    onLikesChanged: (String) -> Unit,
    addBook: () -> Boolean,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TextFieldElement(
                textValue = formState.title,
                label = "Title",
                onValueChange = { onTitleChanged(it) },
                textFieldHasError = formState.title.isEmpty(),
                singleLine = true,
                placeholder = "e.g Rich Dad Poor Dad",
                textErrorMessage = "Title cannot be empty.."
            )
            TextFieldElement(
                textValue = formState.description,
                label = "description",
                onValueChange = { onDescriptionChanged(it) },
                textFieldHasError = formState.description.isEmpty(),
                singleLine = true,
                placeholder = "e.g this book is about money",
                textErrorMessage = "description cannot be empty.."
            )
            TextFieldElement(
                textValue = formState.releaseDate,
                label = "Release Date",
                onValueChange = { onReleaseDateChanged(it) },
                singleLine = true,
                placeholder = "May 12, 2020",
                textErrorMessage = "Release Date cannot be empty..",
                textFieldHasError = formState.releaseDate.isEmpty()

            )
            TextFieldElement(
                textValue = formState.pages.toString(),
                label = "Pages",
                onValueChange = { onPagesChanged(it) },
                textFieldHasError = formState.pages <= 0,
                singleLine = true,
                placeholder = "e.g 200",
                textErrorMessage = "Pages cannot be empty and they should be above 0"
            )
            TextFieldElement(
                textValue = formState.cover,
                label = "Cover",
                onValueChange = { onCoverChanged(it) },
                textFieldHasError = false,
                singleLine = false,
                placeholder = "e.g https://picsum.photos/200",
                textErrorMessage = ""
            )
            TextFieldElement(
                textValue = formState.likes.toString(),
                label = "Likes",
                onValueChange = { onLikesChanged(it) },
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g 100",
                textErrorMessage = ""
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

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
                    val allfieldsAreValid = addBook()
                    if (allfieldsAreValid) {
                        onAddBook()
                        Toast.makeText(context, "Book added successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Please fill all required fields correctly", Toast.LENGTH_SHORT).show()
                    }

                },
                modifier = modifier.weight(1f)
            ) {
                Text(text = "Add")
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
private fun AddBookFormPreview() {
    val initialBookState = AddBookFormState(
        title = "",
        description = "",
        releaseDate = "",
        pages = 0,
        cover = "",
        likes = 0,
        isFavorite = false
    )

    val allFormFieldsAreValid = true

    val context = LocalContext.current
    BookStoreTheme {
        AddBookForm(
            onCancel = {},
            onAddBook = {},
            formState = initialBookState,
            context = context,
            onTitleChanged = { _ -> },
            onDescriptionChanged = { _ -> },
            onReleaseDateChanged = { _ -> },
            onPagesChanged = { _ -> },
            onCoverChanged = { _ -> },
            onLikesChanged = { _ -> },
            addBook = { allFormFieldsAreValid },
            modifier = Modifier
        )
    }
}