package io.thomasgasangwa.bookcollection.presentation.auth.sign_in.sign_in_anonymously

data class SignInAnonymouslyUiState(
    val isLoading: Boolean = false,
    val isSignedInSuccessfully: Boolean = false,
    val errorMessage: String = ""
)
