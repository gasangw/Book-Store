package io.thomasgasangwa.bookstore.presentation.auth

sealed class SignInState {
    object Initial : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val exception: Exception) : SignInState()
}