package io.thomasgasangwa.bookstore.presentation.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldElement(modifier: Modifier = Modifier, textValue: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = textValue,
        onValueChange = onValueChange,
        label = {Text(text = label)},
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium

    )
}