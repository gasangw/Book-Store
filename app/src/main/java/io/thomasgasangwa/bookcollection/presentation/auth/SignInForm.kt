package io.thomasgasangwa.bookcollection.presentation.auth

import BookStoreTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun SignInForm(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var hasAttemptedSubmit by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        TextFieldElement(
            textValue = email,
            label = "email",
            onValueChange = { email = it },
            textFieldHasError = hasAttemptedSubmit && email.isEmpty(),
            singleLine = true,
            placeholder = "example@gmail.com",
            textErrorMessage = "Email cannot be empty.."
        )
        Spacer(modifier = Modifier.height(6.dp))
        PasswordTextField(
            placeholderText = "enter password..."
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                hasAttemptedSubmit = true
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
        SignInForm()
    }
}