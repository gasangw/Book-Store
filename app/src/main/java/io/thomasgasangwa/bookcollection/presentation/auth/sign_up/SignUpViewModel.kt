package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _createUserState = MutableStateFlow(SignUpUiState())
    val createUserState: StateFlow<SignUpUiState> = _createUserState.asStateFlow()


    fun onEmailChange(email: String) {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isNotEmpty()) {
            _createUserState.value = _createUserState.value.copy(
                email = email,
                isEmailValid = isValid
            )
        }
    }

    fun onPasswordChange(password: String) {
        if (password.isNotEmpty()) {
            _createUserState.value = _createUserState.value.copy(
                password = password
            )
        }
    }

    fun onConfirmPasswordChange(password: String) {
        if (password.isNotEmpty()) {
            _createUserState.value = _createUserState.value.copy(
                confirmPassword = password,
                confirmPasswordIsEqualToPassword = _createUserState.value.password.trim() == password.trim()
            )
        }
    }

    fun togglePasswordVisibility() {
        _createUserState.value =
            _createUserState.value.copy(isPasswordVisible = !_createUserState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _createUserState.value = _createUserState.value.copy(
            isConfirmPasswordVisible = !_createUserState.value.isConfirmPasswordVisible
        )
    }

    fun clearSignUpFormInputs() {
        _createUserState.value = _createUserState.value.copy(
            email = "",
            password = "",
            confirmPassword = "",
            confirmPasswordIsEqualToPassword = false,
            isEmailValid = false,
            isPasswordVisible = false,

            isConfirmPasswordVisible = false

        )
    }


    fun createNewUserWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                if (_createUserState.value.email.isNotEmpty() &&
                    _createUserState.value.password.isNotEmpty()
                ) {
                    authRepository.createNewUserWithEmailAndPassword(
                        _createUserState.value.email,
                        _createUserState.value.password
                    )
                } else {
                    throw Exception("Error occurred while signing up user")
                }
            } catch (e: Exception) {
                Timber.e("Error occurred $e")
            }
        }
    }

}