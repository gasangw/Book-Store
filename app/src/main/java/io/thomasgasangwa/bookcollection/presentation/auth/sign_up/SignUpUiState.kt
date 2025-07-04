package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

data class SignUpUiState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val signUpIsSuccessful: Boolean = false,
)

data class SignUpFormUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val confirmPasswordIsEqualToPassword: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
)