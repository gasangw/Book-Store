package io.thomasgasangwa.bookstore.presentation.add_book

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.presentation.view.components.TextFieldElement
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddBook(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onAddBook: () -> Unit
) {

    val addBookViewModel: AddBookViewModel = koinViewModel()
    val formState by addBookViewModel.formState.collectAsStateWithLifecycle()

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
                onValueChange = { addBookViewModel.onTitleChanged(it) },
                textFieldHasError = formState.title.isEmpty(),
                singleLine = true,
                placeholder = "e.g Rich Dad Poor Dad",
                textErrorMessage = "Title cannot be empty.."
            )
            TextFieldElement(
                textValue = formState.description,
                label = "description",
                onValueChange = { addBookViewModel.onDescriptionChanged(it) },
                textFieldHasError = formState.description.isEmpty(),
                singleLine = true,
                placeholder = "e.g this book is about money",
                textErrorMessage = "description cannot be empty.."
            )
            TextFieldElement(
                textValue = formState.releaseDate,
                label = "Release Date",
                onValueChange = { addBookViewModel.onReleaseDateChanged(it) },
                singleLine = true,
                placeholder = "May 12, 2020",
                textErrorMessage = "Release Date cannot be empty..",
                textFieldHasError = formState.releaseDate.isEmpty()

            )
            TextFieldElement(
                textValue = formState.pages.toString(),
                label = "Pages",
                onValueChange = { addBookViewModel.onPagesChanged(it) },
                textFieldHasError = formState.pages <= 0,
                singleLine = true,
                placeholder = "e.g 200",
                textErrorMessage = "Pages cannot be empty and they should be above 0"
            )
            TextFieldElement(
                textValue = formState.cover ?: "",
                label = "Cover",
                onValueChange = { addBookViewModel.onCoverChanged(it) },
                textFieldHasError = false,
                singleLine = false,
                placeholder = "e.g https://picsum.photos/200",
                textErrorMessage = ""
            )
            TextFieldElement(
                textValue = formState.likes.toString(),
                label = "Likes",
                onValueChange = { addBookViewModel.onLikesChanged(it) },
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
                onClick = { addBookViewModel.addBook(); onAddBook() },
                modifier = modifier.weight(1f)
            ) {
                Text(text = "Add")
            }
        }
    }
}
