package io.thomasgasangwa.bookstore.presentation.add_book

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.presentation.view.components.TextFieldElement

@Composable
fun AddBook(modifier: Modifier = Modifier, onAddBook: () -> Unit, onCancel: () -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "New Book", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSecondary , modifier = modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height(30.dp))

        TextFieldElement(textValue = "", label = "Title", onValueChange = {})
        TextFieldElement(textValue = "", label = "Author", onValueChange = {})
        TextFieldElement(textValue = "", label = "Content", onValueChange = {})
        TextFieldElement(textValue = "", label = "Genre", onValueChange = {})

        Row (horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier.fillMaxWidth()){
            Button(onClick = onCancel, modifier = modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                Text(text = "Cancel")
            }
            Button(onClick = onAddBook, modifier = modifier.weight(1f)) {
                Text(text = "Add")
            }
        }
    }
}


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