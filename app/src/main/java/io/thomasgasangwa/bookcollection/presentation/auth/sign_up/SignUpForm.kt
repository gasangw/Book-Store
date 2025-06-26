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
import io.thomasgasangwa.bookcollection.presentation.auth.components.PasswordTextField
import io.thomasgasangwa.bookcollection.presentation.view.components.TextFieldElement

@Composable
fun SignUpForm(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var hasAttemptedSubmit by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
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
        PasswordTextField(
            placeholderText = "enter password..."
        )
        PasswordTextField(
            placeholderText = "confirm password"
        )
        Button(
            onClick = {
                hasAttemptedSubmit = true
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
        SignUpForm()
    }
}
