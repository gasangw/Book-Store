package io.thomasgasangwa.bookcollection.presentation.auth

import BookStoreTheme
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.presentation.auth.components.LoginButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInSuccess: () -> Unit
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()
    val signInState by authViewModel.state.collectAsStateWithLifecycle()

    SigInComponents(
        modifier = modifier,
        context = context,
        signIn = { authViewModel.signIn(context) },
        signInState = signInState,
        onSignInSuccess = onSignInSuccess
    )

}

@Composable
fun SigInComponents(
    modifier: Modifier = Modifier,
    context: Context,
    signIn: (context: Context) -> Unit,
    signInState: SignInState,
    onSignInSuccess: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Glad to See You! \uD83D\uDE0A\uD83D\uDCDA",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(18.dp)
        )

        Spacer(modifier = modifier.fillMaxHeight(0.2f))

        SignInForm()

        Text(text = "Or", style = MaterialTheme.typography.bodyLarge)

        LoginButton(
            context = context,
            signIn = signIn,
            signInState = signInState,
            onSignInSuccess = onSignInSuccess
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BookStoreTheme {
        SigInComponents(
            onSignInSuccess = {},
            context = LocalContext.current,
            signIn = {},
            signInState = SignInState.Initial

        )
    }
}