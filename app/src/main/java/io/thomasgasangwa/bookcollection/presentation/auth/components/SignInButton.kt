package io.thomasgasangwa.bookcollection.presentation.auth.components

import BookStoreTheme
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.presentation.auth.SignInState

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    context: Context,
    signIn: (context: Context) -> Unit,
    signInState: SignInState,
    onSignInSuccess: () -> Unit,
) {


    when (signInState) {
        is SignInState.Initial -> {
            Button(onClick = {
                signIn(context)
            }) {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "google icon",
                        modifier = modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Login with Google")
                }
            }
        }

        is SignInState.Loading -> {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = ProgressIndicatorDefaults.circularColor
            )
        }

        is SignInState.Success -> {
            onSignInSuccess()
        }

        is SignInState.Error -> {
            Text(
                text = "Error occurred while you were trying to SignIn: ${signInState.exception.message}",
                color = MaterialTheme.colorScheme.error,
                modifier = modifier
            )
        }
        is SignInState.SignInUser -> {}
    }
}

@Preview
@Composable
private fun LoginButtonPreview() {
    val context = LocalContext.current
    BookStoreTheme {
        LoginButton(
            context = context,
            signIn = {},
            signInState = SignInState.Initial,
            onSignInSuccess = {}
        )
    }
}