package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import BookStoreTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignUpNavigateToLogin: () -> Unit
) {
    val signUpViewModel: SignUpViewModel = koinViewModel()
    val uiState by signUpViewModel.createUserState.collectAsStateWithLifecycle()
    val signUpFormState by signUpViewModel.signUpFormUiState.collectAsStateWithLifecycle()

    SignUp(
        uiState = uiState,
        signUpFormState = signUpFormState,
        onEmailChange = { it -> signUpViewModel.onEmailChange(it) },
        onPasswordChange = { it -> signUpViewModel.onPasswordChange(it) },
        onConfirmPasswordChange = { it -> signUpViewModel.onConfirmPasswordChange(it) },
        passwordVisible = { signUpViewModel.togglePasswordVisibility() },
        confirmPasswordVisible = { signUpViewModel.toggleConfirmPasswordVisibility() },
        clearSignUpInputFields = { signUpViewModel.clearSignUpFormInputs() },
        createNewUserWithEmailAndPassword = { signUpViewModel.createNewUserWithEmailAndPassword() },
        onSignUpNavigateToLogin = onSignUpNavigateToLogin,
        modifier = modifier
    )
}

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    signUpFormState: SignUpFormUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    passwordVisible: () -> Unit,
    confirmPasswordVisible: () -> Unit,
    clearSignUpInputFields: () -> Unit,
    createNewUserWithEmailAndPassword: () -> Unit,
    onSignUpNavigateToLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.book),
            contentDescription = "book",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = modifier.fillMaxHeight(0.03f))
        SignUpForm(
            uiState = uiState,
            signUpFormState = signUpFormState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onConfirmPasswordChange = onConfirmPasswordChange,
            passwordVisible = passwordVisible,
            confirmPasswordVisible = confirmPasswordVisible,
            clearSignUpInputFields = clearSignUpInputFields,
            createNewUserWithEmailAndPassword = createNewUserWithEmailAndPassword,
            onSignUpNavigateToLogin = onSignUpNavigateToLogin,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    BookStoreTheme {
        SignUpScreen(
            onSignUpNavigateToLogin = {}
        )
    }
}