package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SignInState>(SignInState.Initial)
    val state: StateFlow<SignInState> = _state.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> = _formState.asStateFlow()

    init {
        currentUser()
    }
//
//    fun signInWithGoogle(context: Context) {
//        _state.value = SignInState.Loading
//        viewModelScope.launch {
//            when (val result = authRepository.signIn(context)) {
//                is Result.Success -> {
//                    _state.value = SignInState.SignInUser(result.value)
//                    _state.value = SignInState.Success
//                }
//
//                is Result.Failure -> {
//                    _state.value = SignInState.Error(Exception(result.exception))
//                }
//            }
//
//        }
//    }


    fun signInAnonymously() {
        _state.value = SignInState.Loading
        viewModelScope.launch {
            try {
                val user = authRepository.signInAnonymously()
                _state.value = when (user) {
                    is Result.Failure -> SignInState.Error(Exception("Error occurred while getting a current user"))
                    is Result.Success<*> -> {
                        Timber.Forest.e("user logged in ${user.value}")
                        SignInState.SignInUser(user.value as User)
                    }
                }
                _state.value = SignInState.Success
            } catch (e: Exception) {
                _state.value = SignInState.Error(e)
            }
        }
    }

    fun onEmailChange(email: String) {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                email = email,
                isEmailValid = isValid
            )
        }
    }

    fun onPasswordChange(password: String) {
        if(password.isNotEmpty()){
            _formState.value = _formState.value.copy(
                password = password,
                isPasswordValid = true
            )
        }
    }

    fun togglePasswordVisible(){
        _formState.value = _formState.value.copy(isPasswordVisible = !_formState.value.isPasswordVisible)
    }

    fun clearSignInFormInputs(){
        _formState.value = _formState.value.copy(
            email = "",
            password = ""
        )
    }


    fun signInWithEmailAndPassword() {
        Timber.e("form State ${_formState.value.email}, ${_formState.value.password}")
    }

    fun currentUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _state.value = when (user) {
                is Result.Failure -> SignInState.Error(Exception("Error occurred while getting a current user"))
                is Result.Success<*> -> SignInState.SignInUser(user.value as User?)
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _state.value = SignInState.SignInUser(null)
        }
    }
}