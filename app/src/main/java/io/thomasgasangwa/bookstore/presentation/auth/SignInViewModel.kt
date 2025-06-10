package io.thomasgasangwa.bookstore.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import io.thomasgasangwa.bookstore.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    // private val authRepository: AuthRepository
) : ViewModel() {
//    private val _state = mutableStateOf(SignInState())
//    val state: State<SignInState> = _state


    private fun signIn() {
        viewModelScope.launch {
            // authRepository.signIn(context)
        }

    }
}