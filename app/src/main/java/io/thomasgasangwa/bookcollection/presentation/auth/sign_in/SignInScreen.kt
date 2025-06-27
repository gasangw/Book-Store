package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInSuccess: () -> Unit,
    onSignUpButtonClick: () -> Unit,
) {

//    val context = LocalContext.current
    val signInViewModel: SignInViewModel = koinViewModel()
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val formState by signInViewModel.formState.collectAsStateWithLifecycle()

    LaunchedEffect(signInState) {
        if (signInState is SignInState.Success) {
            onSignInSuccess()
        }
    }

    SigInComponents(
        modifier = modifier,
//        context = context,
//        signIn = { authViewModel.signInWithGoogle(context) },
        formState = formState,
        onEmailChange = { it -> signInViewModel.onEmailChange(it) },
        onPasswordChange = { it -> signInViewModel.onPasswordChange(it) },
        togglePasswordVisible = { signInViewModel.togglePasswordVisible() },
        onClickSignInButton = { signInViewModel.signInWithEmailAndPassword() },
        clearSignInFormInputs = { signInViewModel.clearSignInFormInputs() },
        signInState = signInState,
        onSignUpButtonClick = onSignUpButtonClick,
        signInAnonymously = { signInViewModel.signInAnonymously() }
    )

    if (signInState is SignInState.Error) {
        Text(
            text = (signInState as SignInState.Error).exception.message ?: "Unknown error",
            color = Color.Red,
            modifier = Modifier.padding(8.dp)
        )
    }

}

@Composable
fun SigInComponents(
    modifier: Modifier = Modifier,
//    context: Context,
//    signIn: (context: Context) -> Unit,
    formState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisible: () -> Unit,
    onClickSignInButton: () -> Unit,
    clearSignInFormInputs: () -> Unit,
    signInState: SignInState,
    onSignUpButtonClick: () -> Unit,
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

        SignInForm(
            formState = formState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            togglePasswordVisible = togglePasswordVisible,
            onClickSignInButton = onClickSignInButton,
            clearSignInFormInputs = clearSignInFormInputs
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Doesn't have account?",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
        TextButton(onClick = { onSignUpButtonClick() }) {
            Text(
                text = "SIGN UP",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surfaceTint
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
//        LoginWithGoogleButton(
//            context = context,
//            signIn = signIn,
//            signInState = signInState,
//            onSignInSuccess = onSignInSuccess
//        )

//
//        TextButton(onClick = { signInAnonymously() }) {
//            when (signInState) {
//                is SignInState.Error -> {
//                    Text(
//                        text = "error occurred while logging in",
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//
//                SignInState.Loading -> {
//                    CircularProgressIndicator(
//                        strokeWidth = 2.dp,
//                        color = ProgressIndicatorDefaults.circularColor,
//                        modifier = Modifier.padding(5.dp)
//                    )
//                }
//
//                else -> null
//            }
//            Text(text = "Continue without Signing In", style = MaterialTheme.typography.bodyLarge)
//        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BookStoreTheme {
        SigInComponents(
            signInState = SignInState.Initial,
            onSignUpButtonClick = {},
            signInAnonymously = {},
            formState = LoginFormState(),
            onEmailChange = {},
            onPasswordChange = {},
            togglePasswordVisible = {},
            onClickSignInButton = {},
            clearSignInFormInputs = {}
        )
    }
}