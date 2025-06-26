package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

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
import io.thomasgasangwa.bookcollection.presentation.auth.components.PasswordTextField
import io.thomasgasangwa.bookcollection.presentation.view.components.TextFieldElement

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    formState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisible: () -> Unit,
    onClickSignInButton: () -> Unit,
    clearSignInFormInputs: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var hasAttemptedSubmit by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextFieldElement(
            textValue = formState.email,
            label = "email",
            onValueChange = { onEmailChange(it) },
            textFieldHasError = hasAttemptedSubmit && email.isEmpty() && !formState.isEmailValid,
            singleLine = true,
            placeholder = "example@gmail.com",
            textErrorMessage = "Kindly check the email field.."
        )
        PasswordTextField(
            onPasswordChange = onPasswordChange,
            formState = formState,
            placeholderText = "enter password...",
            togglePasswordVisible = togglePasswordVisible,
            hasAttemptedSubmit = hasAttemptedSubmit
        )
        Button(
            onClick = {
                hasAttemptedSubmit = true
                onClickSignInButton()
                clearSignInFormInputs()
            },
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Sign In",
                modifier = modifier
                    .padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInFormPreview() {
    BookStoreTheme {
        SignInForm(
            formState = LoginFormState(),
            onEmailChange = {},
            onPasswordChange = {},
            togglePasswordVisible = {},
            onClickSignInButton = {},
            clearSignInFormInputs = {}
        )
    }
}