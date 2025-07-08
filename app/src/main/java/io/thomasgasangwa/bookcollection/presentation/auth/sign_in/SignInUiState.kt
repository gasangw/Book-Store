package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

data class SignInUiState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val signInIsSuccessful: Boolean = false,
    val deleteUserSuccessfully: Boolean = false,
)

data class LoginFormUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailValid: Boolean = false,
)