package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import androidx.lifecycle.ViewModel
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
            if (_createUserState.value.password.trim() == password.trim()) {
                _createUserState.value = _createUserState.value.copy(
                    confirmPassword = password,
                    confirmPasswordIsEqualToPassword = true
                )
            } else {
                _createUserState.value = _createUserState.value.copy(
                    confirmPasswordIsEqualToPassword = false
                )
            }
        }
    }

    fun togglePasswordVisible() {
        _createUserState.value =
            _createUserState.value.copy(isPasswordVisible = !_createUserState.value.isPasswordVisible)
    }

    fun clearSignUpFormInputs() {
        _createUserState.value = _createUserState.value.copy(
            email = "",
            password = "",
            confirmPassword = ""
        )
    }


    fun createNewUserWithEmailAndPassword() {

    }

}