package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.components.ConfirmPasswordTextField
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.components.SignUpPasswordTextField
import io.thomasgasangwa.bookcollection.presentation.view.components.TextFieldElement

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    state: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    passwordVisible: () -> Unit,
    confirmPasswordVisible: () -> Unit,
    clearSignUpInputFields: () -> Unit,
    createNewUserWithEmailAndPassword: () -> Unit,
    onSignUpNavigateToLogin: () -> Unit
) {
    var hasAttemptedSubmit by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        TextFieldElement(
            textValue = state.email,
            label = "email",
            onValueChange = { onEmailChange(it) },
            textFieldHasError = hasAttemptedSubmit && state.email.isEmpty(),
            singleLine = true,
            placeholder = "example@gmail.com",
            textErrorMessage = "Email cannot be empty.."
        )
        SignUpPasswordTextField(
            state = state,
            placeholderText = "Password",
            onPasswordChange = onPasswordChange,
            hasAttemptedSubmit = hasAttemptedSubmit,
            passwordVisible = passwordVisible
        )
        ConfirmPasswordTextField(
            state = state,
            placeholderText = "Confirm Password",
            onConfirmPasswordChange = onConfirmPasswordChange,
            hasAttemptedSubmit = hasAttemptedSubmit,
            confirmPasswordVisible = confirmPasswordVisible
        )

        Button(
            onClick = {
                hasAttemptedSubmit = true
                createNewUserWithEmailAndPassword()
                clearSignUpInputFields()
                onSignUpNavigateToLogin()
            },
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Sign Up",
                modifier = modifier
                    .padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpFormPreview() {
    BookStoreTheme {
        SignUpForm(
            state = SignUpUiState(),
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            passwordVisible = {},
            confirmPasswordVisible = {},
            clearSignUpInputFields = {},
            createNewUserWithEmailAndPassword = {},
            onSignUpNavigateToLogin ={}

        )
    }
}
