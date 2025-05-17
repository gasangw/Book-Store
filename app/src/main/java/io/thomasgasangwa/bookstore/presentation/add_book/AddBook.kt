package io.thomasgasangwa.bookstore.presentation.add_book

import BookStoreTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.presentation.view.components.TextFieldElement

@Composable
fun AddBook(modifier: Modifier = Modifier, onAddBook: () -> Unit, onCancel: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TextFieldElement(
                textValue = "",
                label = "Title",
                onValueChange = {},
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g Rich Dad Poor Dad",
                textErrorMessage = "Title cannot be empty.."
            )
            TextFieldElement(
                textValue = "",
                label = "description",
                onValueChange = {},
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g this book is about money",
                textErrorMessage = "description cannot be empty.."
            )
            TextFieldElement(
                textValue = "",
                label = "Release Date",
                onValueChange = {},
                singleLine = false,
                placeholder = "2020",
                textErrorMessage = "Release Date cannot be empty..",
                textFieldHasError = false

            )
            TextFieldElement(textValue = "", label = "Pages", onValueChange = {},
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g 200",
                textErrorMessage = "Pages cannot be empty.."
            )
            TextFieldElement(textValue = "", label = "Cover", onValueChange = {},
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g https://picsum.photos/200",
                textErrorMessage = ""
            )
            TextFieldElement(textValue = "", label = "Likes", onValueChange = {},
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
            Button(onClick = onAddBook, modifier = modifier.weight(1f)) {
                Text(text = "Add")
            }
        }
    }
}


data class Book(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val description: String,
    val pages: Int,
    val cover: String?,
    val likes: Int?,
)


@PreviewLightDark
@Composable
private fun AddBookPreview() {
    BookStoreTheme {
        AddBook(
            onAddBook = {},
            onCancel = {}
        )
    }
}