package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _createUserState = MutableStateFlow(SignUpUiState())
    val createUserState: StateFlow<SignUpUiState> = _createUserState.asStateFlow()

    private val _signUpFormUiState = MutableStateFlow(SignUpFormUiState())
    val signUpFormUiState: StateFlow<SignUpFormUiState> = _signUpFormUiState.asStateFlow()


    fun onEmailChange(email: String) {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isNotEmpty()) {
            _signUpFormUiState.value = _signUpFormUiState.value.copy(
                email = email,
                isEmailValid = isValid
            )
        }
    }

    fun onPasswordChange(password: String) {
        if (password.isNotEmpty()) {
            _signUpFormUiState.value = _signUpFormUiState.value.copy(
                password = password
            )
        }
    }

    fun onConfirmPasswordChange(password: String) {
        if (password.isNotEmpty()) {
            _signUpFormUiState.value = _signUpFormUiState.value.copy(
                confirmPassword = password,
                confirmPasswordIsEqualToPassword = _signUpFormUiState.value.password.trim() == password.trim()
            )
        }
    }

    fun togglePasswordVisibility() {
        _signUpFormUiState.value =
            _signUpFormUiState.value.copy(isPasswordVisible = !_signUpFormUiState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _signUpFormUiState.value = _signUpFormUiState.value.copy(
            isConfirmPasswordVisible = !_signUpFormUiState.value.isConfirmPasswordVisible
        )
    }

    fun clearSignUpFormInputs() {
        _signUpFormUiState.value = _signUpFormUiState.value.copy(
            email = "",
            password = "",
            confirmPassword = ""
        )
    }

    fun createNewUserWithEmailAndPassword() {
        _createUserState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                if (_signUpFormUiState.value.email.isNotEmpty() &&
                    _signUpFormUiState.value.password.isNotEmpty()
                ) {
                    authRepository.createNewUserWithEmailAndPassword(
                        _signUpFormUiState.value.email,
                        _signUpFormUiState.value.password
                    )
                    _createUserState.update {
                        it.copy(
                            isLoading = false,
                            signUpIsSuccessful = true
                        )
                    }
                } else {
                    _createUserState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = Exception("Error occurred while signing up user")
                        )
                    }
                }
            } catch (e: Exception) {
                _createUserState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e
                    )
                }
            }
        }
    }

}