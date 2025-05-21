package io.thomasgasangwa.bookstore.presentation.update_book

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

    val bookState = (updateBookState as UpdateBookState.Success).book

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
            TextFieldElement(
                textValue = bookState.title,
                label = "Title",
                onValueChange = { updateBookViewModel.updateTitle(it) },
                textFieldHasError = bookState.title.isEmpty(),
                singleLine = true,
                placeholder = "e.g Rich Dad Poor Dad",
                textErrorMessage = "Title cannot be empty.."
            )
            TextFieldElement(
                textValue = bookState.description,
                label = "description",
                onValueChange = { updateBookViewModel.updateDescription(it) },
                textFieldHasError = bookState.description.isEmpty(),
                singleLine = true,
                placeholder = "e.g this book is about money",
                textErrorMessage = "description cannot be empty.."
            )
            TextFieldElement(
                textValue = bookState.releaseDate,
                label = "Release Date",
                onValueChange = { updateBookViewModel.updateReleaseDate(it) },
                singleLine = false,
                placeholder = "July 23, 2020",
                textErrorMessage = "Release Date cannot be empty..",
                textFieldHasError = bookState.releaseDate.isEmpty()

            )
            TextFieldElement(
                textValue = bookState.pages.toString(), label = "Pages",
                onValueChange = { updateBookViewModel.updatePages(it) },
                textFieldHasError = bookState.pages <= 0,
                singleLine = true,
                placeholder = "e.g 200",
                textErrorMessage = "Pages cannot be empty.."
            )
            TextFieldElement(
                textValue = bookState.cover,
                label = "Cover",
                onValueChange = { updateBookViewModel.updateCover(it) },
                textFieldHasError = false,
                singleLine = true,
                placeholder = "e.g https://picsum.photos/200",
                textErrorMessage = ""
            )
            TextFieldElement(
                textValue = bookState.likes.toString(), label = "Likes",
                onValueChange = { updateBookViewModel.updateLikes(it) },
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
                onClick = { updateBookViewModel.updateBook(); onUpdateBook() },
                modifier = modifier.weight(1f)
            ) {
                Text(text = "Update")
            }
        }
    }
}


//@PreviewLightDark
//@Composable
//private fun UpdateBookPreview() {
//    BookStoreTheme {
//        val
//        UpdateBook(
//            onCancel = {}
//            book = BookParcelableData
//        )
//    }
//}