package io.thomasgasangwa.bookcollection.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SignInState>(SignInState.Initial)
    val state: StateFlow<SignInState> = _state.asStateFlow()


    fun signIn(context: Context) {
        _state.value = SignInState.Loading
        viewModelScope.launch {
            when (val result = authRepository.signIn(context)) {
                is Result.Success -> {
                    _state.value = SignInState.SignInUser(result.value)
                    _state.value = SignInState.Success
                }

                is Result.Failure -> {
                    _state.value = SignInState.Error(Exception(result.exception))
                }
            }

        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}