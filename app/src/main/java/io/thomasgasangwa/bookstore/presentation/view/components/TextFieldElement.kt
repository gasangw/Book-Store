package io.thomasgasangwa.bookstore.presentation.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldElement(
    modifier: Modifier = Modifier,
    textValue: String,
    label: String,
    onValueChange: (String) -> Unit,
    textFieldHasError: Boolean,
    textErrorMessage: String,
    singleLine: Boolean,
    placeholder: String
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = singleLine,
        isError = textFieldHasError,
        textStyle = MaterialTheme.typography.bodyMedium,
        supportingText = {
            if (textFieldHasError) {
                Text(text = textErrorMessage)
            }
        },
        shape = MaterialTheme.shapes.small,

        modifier = modifier.fillMaxWidth()
    )
}