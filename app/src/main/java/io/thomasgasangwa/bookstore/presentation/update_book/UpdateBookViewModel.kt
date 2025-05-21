package io.thomasgasangwa.bookstore.presentation.update_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateBookViewModel(
    private val book: Book,
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow<UpdateBookState>(UpdateBookState.Success(book))
    val state: StateFlow<UpdateBookState> = _state.asStateFlow()

    init {
        updateBook()
    }

    fun getCurrentBook(): Book {
        return (_state.value as UpdateBookState.Success).book
    }

    fun updateTitle(title: String) {
        val currentBook = getCurrentBook()
        _state.value = UpdateBookState.Success(currentBook.copy(title = title))
    }

    fun updateDescription(description: String) {
        _state.value = UpdateBookState.Success(getCurrentBook().copy(description = description))
    }

    fun updateReleaseDate(releaseDate: String) {
        _state.value = UpdateBookState.Success(getCurrentBook().copy(releaseDate = releaseDate))
    }

    fun updatePages(pages: String) {
        val pages = pages.toIntOrNull() ?: 0
        _state.value = UpdateBookState.Success(getCurrentBook().copy(pages = pages))
    }

    fun updateCover(cover: String) {
        _state.value = UpdateBookState.Success(getCurrentBook().copy(cover = cover))
    }

    fun updateLikes(likes: String) {
        val likes = likes.toIntOrNull() ?: 0
        _state.value = UpdateBookState.Success(getCurrentBook().copy(likes = likes))
    }

    fun updateBook() {
        viewModelScope.launch {
            try {
                localRepository.updateBook(getCurrentBook())
            } catch (e: Exception) {
                _state.value = UpdateBookState.Error(e)
            }
        }
    }
}