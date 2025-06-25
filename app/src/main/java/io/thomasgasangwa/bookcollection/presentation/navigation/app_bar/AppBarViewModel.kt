package io.thomasgasangwa.bookcollection.presentation.navigation.app_bar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppBarViewModel : ViewModel() {
    private val _state = MutableStateFlow(DropDownUiState())
    val state: StateFlow<DropDownUiState> = _state.asStateFlow()


    fun expandDropdown() {
        _state.update { state -> state.copy(isExpanded = !state.isExpanded) }
    }

    fun dismissDropdown() {
        _state.update { state -> state.copy(isExpanded = false) }
    }
}