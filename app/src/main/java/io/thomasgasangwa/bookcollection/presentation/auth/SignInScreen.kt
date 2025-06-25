package io.thomasgasangwa.bookcollection.presentation.auth

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInSuccess: () -> Unit
) {
//    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()
    val signInState by authViewModel.state.collectAsStateWithLifecycle()

    SigInComponents(
        modifier = modifier,
//        context = context,
//        signIn = { authViewModel.signInWithGoogle(context) },
        signInState = signInState,
        onSignInSuccess = onSignInSuccess,
        signInAnonymously = { authViewModel.signInAnonymously() }
    )

}

@Composable
fun SigInComponents(
    modifier: Modifier = Modifier,
//    context: Context,
//    signIn: (context: Context) -> Unit,
    signInState: SignInState,
    onSignInSuccess: () -> Unit,
    signInAnonymously: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Glad to See You! \uD83D\uDE0A\uD83D\uDCDA",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(18.dp)
        )

        SignInForm()

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Or", style = MaterialTheme.typography.bodyLarge)

//        LoginWithGoogleButton(
//            context = context,
//            signIn = signIn,
//            signInState = signInState,
//            onSignInSuccess = onSignInSuccess
//        )


        TextButton(onClick = { signInAnonymously() }) {
            when (signInState) {
                is SignInState.Error -> {}
                SignInState.Initial -> {}
                SignInState.Loading -> {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = ProgressIndicatorDefaults.circularColor
                    )
                }

                is SignInState.SignInUser -> {}
                SignInState.Success -> {
                    onSignInSuccess()
                }
            }
            Text(text = "Continue without Signing In", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BookStoreTheme {
        SigInComponents(
            onSignInSuccess = {},
            signInState = SignInState.Initial,
            signInAnonymously = {}
        )
    }
}