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
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.SignUpFormUiState

@Composable
fun SignUpPasswordTextField(
    modifier: Modifier = Modifier,
    signUpFormState: SignUpFormUiState,
    placeholderText: String,
    onPasswordChange: (String) -> Unit,
    hasAttemptedSubmit: Boolean,
    passwordVisible: () -> Unit,
) {
    OutlinedTextField(
        value = signUpFormState.password,
        onValueChange = { onPasswordChange(it) },
        placeholder = { Text(placeholderText) },
        visualTransformation = if (signUpFormState.isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                imageVector = if (signUpFormState.isPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff,
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { passwordVisible() }
            )
        },
        isError = hasAttemptedSubmit && signUpFormState.password.isEmpty(),
        supportingText = {
            if (hasAttemptedSubmit && signUpFormState.password.isEmpty()) {
                Text(text = "Password can't be empty")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun SignUpPasswordTextFieldPreview() {
    BookStoreTheme {
        SignUpPasswordTextField(
            placeholderText = "Enter Password...",
            hasAttemptedSubmit = false,
            signUpFormState = SignUpFormUiState(),
            onPasswordChange = {},
            passwordVisible = {},
        )
    }
}