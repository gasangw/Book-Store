package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

data class SignInUiState(
    val isLoading: Boolean = false,
    val errorMessage: Exception? = null,
    val signInIsSuccessful: Boolean = false
)
//sealed class SignInState {
//    object Initial : SignInState()
//    object AnonymousSignInLoading : SignInState()
//    object SignInEmailAndPasswordLoading : SignInState()
//    object AnonymousSuccess : SignInState()
//    object SignInEmailAndPasswordSuccess : SignInState()
//    data class SignInUser(val currentUser: User?) : SignInState()
//    data class AnonymousSignInError(val exception: Exception) : SignInState()
//    data class SignInEmailAndPasswordError(val exception: Exception) : SignInState()
//    data class CurrentUserError(val exception: Exception) : SignInState()
//}
// split the signIn state in two creteria.

data class LoginFormUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailValid: Boolean = false,
)