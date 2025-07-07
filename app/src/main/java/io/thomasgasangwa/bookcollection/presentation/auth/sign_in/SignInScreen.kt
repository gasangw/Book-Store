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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.presentation.auth.components.CircularProgressIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpButtonClick: () -> Unit,
    onSignInNavigateToHomeScreen: () -> Unit
) {

//    val context = LocalContext.current
    val signInViewModel: SignInViewModel = koinViewModel()
    val signUiInState by signInViewModel.state.collectAsStateWithLifecycle()
    val formState by signInViewModel.formState.collectAsStateWithLifecycle()

    SigInComponents(
        modifier = modifier,
//        context = context,
//        signIn = { authViewModel.signInWithGoogle(context) },
        formState = formState,
        onEmailChange = { it -> signInViewModel.onEmailChange(it) },
        onPasswordChange = { it -> signInViewModel.onPasswordChange(it) },
        togglePasswordVisible = { signInViewModel.togglePasswordVisible() },
        onClickSignInButton = { signInViewModel.signInWithEmailAndPassword() },
        signInAnonymously = { signInViewModel.signInAnonymously() },
        signInUiState = signUiInState,
        onSignUpButtonClick = onSignUpButtonClick,
        onSignInNavigateToHomeScreen = onSignInNavigateToHomeScreen
    )
}

@Composable
fun SigInComponents(
    modifier: Modifier = Modifier,
//    context: Context,
//    signIn: (context: Context) -> Unit,
    formState: LoginFormUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    togglePasswordVisible: () -> Unit,
    onClickSignInButton: () -> Unit,
    signInAnonymously: () -> Unit,
    signInUiState: SignInUiState,
    onSignUpButtonClick: () -> Unit,
    onSignInNavigateToHomeScreen: () -> Unit
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
            signInUiState = signInUiState,
            formState = formState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            togglePasswordVisible = togglePasswordVisible,
            onClickSignInButton = onClickSignInButton,
            onSignInNavigateToHomeScreen = onSignInNavigateToHomeScreen
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


        if (signInUiState.isLoading) {
            CircularProgressIndicator()
        }
        when (signInUiState) {
            is SignInUiState -> {
                if (signInUiState.signInIsSuccessful) {
                    onSignInNavigateToHomeScreen()
                }
            }
        }

        TextButton(onClick = {
            signInAnonymously()
            onSignInNavigateToHomeScreen()
        }) {
            Text(
                text = "Continue without Signing In",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BookStoreTheme {
        SigInComponents(
            signInUiState = SignInUiState(),
            onSignUpButtonClick = {},
            formState = LoginFormUiState(),
            onEmailChange = {},
            onPasswordChange = {},
            togglePasswordVisible = {},
            onClickSignInButton = {},
            signInAnonymously = {},
            onSignInNavigateToHomeScreen = {}
        )
    }
}