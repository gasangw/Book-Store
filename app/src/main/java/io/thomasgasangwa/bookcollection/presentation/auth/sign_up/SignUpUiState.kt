package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val confirmPasswordIsEqualToPassword: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false
)