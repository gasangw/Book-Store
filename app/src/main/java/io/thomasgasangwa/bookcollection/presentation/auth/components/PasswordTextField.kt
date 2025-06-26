package io.thomasgasangwa.bookcollection.presentation.auth.components

import BookStoreTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.LoginFormState

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    formState: LoginFormState,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisible: () -> Unit,
    placeholderText: String,
    hasAttemptedSubmit: Boolean
) {
    OutlinedTextField(
        value = formState.password,
        onValueChange = { onPasswordChange(it) },
        placeholder = { Text(placeholderText) },
        visualTransformation = if (formState.isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                imageVector = if (formState.isPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff,
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { togglePasswordVisible() }
            )
        },
        isError = hasAttemptedSubmit && formState.password.isEmpty(),
        supportingText = {
            if (hasAttemptedSubmit && formState.password.isEmpty()) {
                Text(text = "Password can't be empty")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun PasswordTextFieldPreview() {
    BookStoreTheme {
        PasswordTextField(
            placeholderText = "Enter Password...",
            formState = LoginFormState(),
            onPasswordChange = {},
            togglePasswordVisible = {},
            hasAttemptedSubmit = false
        )
    }
}