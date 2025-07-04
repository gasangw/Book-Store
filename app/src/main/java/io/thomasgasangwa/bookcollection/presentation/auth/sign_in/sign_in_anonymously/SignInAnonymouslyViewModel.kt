//package io.thomasgasangwa.bookcollection.presentation.auth.sign_in.sign_in_anonymously
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class SignInAnonymouslyViewModel(
//    private val authRepository: AuthRepository
//) : ViewModel() {
//    private val _uiState = MutableStateFlow(SignInAnonymouslyUiState())
//    val uiState: StateFlow<SignInAnonymouslyUiState> = _uiState.asStateFlow()
//
//}