package io.thomasgasangwa.bookstore.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

        localRepository.getFavoriteBooksStream().map { favoriteBooksList ->
            FavoriteBookState.Success(favoriteBooksList) as FavoriteBookState
        }.onStart {
            _state.value = FavoriteBookState.Loading(value = true)
        }.onEach { favoriteBookState ->
            _state.value = favoriteBookState
        }.onCompletion {
            _state.value = FavoriteBookState.Loading(value = false)
        }.catch {
            _state.value = FavoriteBookState.Error(it as Exception)
        }.launchIn(viewModelScope)

//        _state.update { FavoriteBookState.Loading(value = true) }
//        viewModelScope.launch {
//            localRepository.getFavoriteBooksStream().collect { result ->
//                try {
//                    _state.update { FavoriteBookState.Success(books = result) }
//                } catch (e: Exception) {
//                    _state.update { FavoriteBookState.Error(exception = e) }
//                }
//            }
//        }
//        _state.update { FavoriteBookState.Loading(value = false) }
    }

    fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            localRepository.updateFavoriteStatus(id, isFavorite)
        }
    }
}