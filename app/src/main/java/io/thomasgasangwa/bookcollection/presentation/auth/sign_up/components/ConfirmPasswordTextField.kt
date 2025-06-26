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
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.SignUpUiState

@Composable
fun ConfirmPasswordTextField(
    modifier: Modifier = Modifier,
    state: SignUpUiState,
    placeholderText: String,
    onConfirmPasswordChange: (String) -> Unit,
    hasAttemptedSubmit: Boolean,
    confirmPasswordVisible: () -> Unit

) {
    OutlinedTextField(
        value = state.confirmPassword,
        onValueChange = { it -> onConfirmPasswordChange(it) },
        placeholder = { Text(placeholderText) },
        visualTransformation = if (state.isConfirmPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                imageVector = if (state.isConfirmPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff,
                contentDescription = "Toggle confirm password visibility",
                modifier = Modifier.clickable { confirmPasswordVisible() }
            )
        },
        isError = hasAttemptedSubmit &&
                !state.confirmPasswordIsEqualToPassword,
        supportingText = {
            if (hasAttemptedSubmit &&
                !state.confirmPasswordIsEqualToPassword
            ) {
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
            placeholderText = "Confirm Password",
            state = SignUpUiState(),
            onConfirmPasswordChange = {},
            hasAttemptedSubmit = false,
            confirmPasswordVisible = {},
        )
    }
}