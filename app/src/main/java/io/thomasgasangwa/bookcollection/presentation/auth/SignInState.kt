package io.thomasgasangwa.bookcollection.presentation.auth

import io.thomasgasangwa.bookcollection.domain.model.User

sealed class SignInState {
    object Initial : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class SignInUser(val currentUser: User?) : SignInState()
    data class Error(val exception: Exception) : SignInState()
}
