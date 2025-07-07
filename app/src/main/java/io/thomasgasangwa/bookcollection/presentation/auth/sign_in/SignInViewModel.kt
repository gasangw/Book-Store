package io.thomasgasangwa.bookcollection.presentation.auth.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignInUiState())
    val state: StateFlow<SignInUiState> = _state.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormUiState())
    val formState: StateFlow<LoginFormUiState> = _formState.asStateFlow()

    val currentUserIsLoggedIn: StateFlow<Boolean?> = authRepository.currentUserIsLoggedIn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    init {
        currentUser()
    }

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
        if (password.isNotEmpty()) {
            _formState.value = _formState.value.copy(
                password = password
            )
        }
    }

    fun togglePasswordVisible() {
        _formState.value =
            _formState.value.copy(isPasswordVisible = !_formState.value.isPasswordVisible)
    }

//    fun clearSignInFormInputs() {
//        _formState.value = _formState.value.copy(
//            email = "",
//            password = ""
//        )
//    }

    fun signInAnonymously() {
        _state.value = SignInUiState(isLoading = true)
        viewModelScope.launch {
            try {
                authRepository.signInAnonymously()
                SignInUiState(signInIsSuccessful = true)
            } catch (e: Exception) {
                _state.value = SignInUiState(errorMessage = e)
            }
        }
        _state.value = SignInUiState(isLoading = false)
    }


    fun signInWithEmailAndPassword() {
        _state.update { state ->
            state.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        viewModelScope.launch {
            try {
                if (_formState.value.email.isNotEmpty() && _formState.value.password.isNotEmpty()) {
                    val user = authRepository.signInWithEmailAndPassword(
                        _formState.value.email,
                        _formState.value.password
                    )
                    _state.update {
                        when (user) {
                            is Result.Failure -> it.copy(
                                isLoading = false,
                                errorMessage = Exception("Error occurred while signing in")
                            )

                            is Result.Success<*> -> it.copy(
                                isLoading = false,
                                signInIsSuccessful = true
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            errorMessage = Exception("Email or password cannot be empty"),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e, isLoading = false) }
            }
        }
    }


    fun currentUser() {
        viewModelScope.launch {
            _state.value = SignInUiState(isLoading = true)
            try {
                viewModelScope.launch {
                    val currentUser = authRepository.getCurrentUser()
                    when (currentUser) {
                        is Result.Failure -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = Exception("User information can't be retrieved")
                                )
                            }
                        }

                        is Result.Success<*> -> {
                            _state.update { state ->
                                state.copy(
                                    isLoading = false,
                                    user = currentUser.value as User?
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e, isLoading = false) }
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun deleteAccount() {
        _state.update { state ->
            state.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            authRepository.deleteAccount()
            _state.update { state ->
                state.copy(
                    isLoading = false,
                    deleteUserSuccessfully = true
                )
            }
        }
    }

}