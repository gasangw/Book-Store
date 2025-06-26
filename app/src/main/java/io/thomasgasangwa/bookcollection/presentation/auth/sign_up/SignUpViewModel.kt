package io.thomasgasangwa.bookcollection.presentation.auth.sign_up

import androidx.lifecycle.ViewModel
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        authRepository
    }

}