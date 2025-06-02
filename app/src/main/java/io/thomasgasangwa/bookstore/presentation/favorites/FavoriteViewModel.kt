package io.thomasgasangwa.bookstore.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteBookState>(FavoriteBookState.Success(emptyList()))
    val state: StateFlow<FavoriteBookState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavoriteBookState.Success(emptyList())
    )

    init {
        getFavoriteBooks()
    }

    fun getFavoriteBooks() {

        _state.update { FavoriteBookState.Loading(value = true) }
        viewModelScope.launch {
            localRepository.getFavoriteBooksStream().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { FavoriteBookState.Success(books = result.value) }
                    }

                    is Result.Failure -> {
                        _state.update { FavoriteBookState.Error(exception = result.exception as Exception) }
                    }
                }
            }
        }
        // _state.update { FavoriteBookState.Loading(value = false) }
    }

    fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            localRepository.updateFavoriteStatus(id, isFavorite)
        }
    }
}