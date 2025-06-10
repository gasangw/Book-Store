package io.thomasgasangwa.bookstore.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SignInState>(SignInState.Initial)
    val state: StateFlow<SignInState> = _state.asStateFlow()


    fun signIn(context: Context) {
        _state.value = SignInState.Loading
        viewModelScope.launch {
            try {
                authRepository.signIn(context)
                _state.value = SignInState.Success
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }

        }
    }
}