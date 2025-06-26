package io.thomasgasangwa.bookcollection.presentation.auth.sign_up.components

import BookStoreTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmPasswordTextField(
    modifier: Modifier = Modifier,
    placeholderText: String
) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(placeholderText) },
        visualTransformation = if (true)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                imageVector = if (true)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff,
                contentDescription = "Toggle confirm password visibility",
                modifier = Modifier.clickable {  }
            )
        },
        isError = false,
        supportingText = {
            if (true) {
                Text(text = "Confirm Password should be the same as password")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun ConfirmPasswordTextFieldPreview() {
    BookStoreTheme {
        ConfirmPasswordTextField(
            placeholderText = "Confirm Password"
        )
    }
}