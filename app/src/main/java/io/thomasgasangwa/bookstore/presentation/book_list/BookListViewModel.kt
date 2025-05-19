package io.thomasgasangwa.bookstore.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow<BookListState>(BookListState.Success(emptyList()))
    val state: StateFlow<BookListState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookListState.Success(emptyList())
    )

    init {
        getAllBooks()
    }

    private fun getAllBooks() {
        _state.update { BookListState.Loading(value = true) }
        viewModelScope.launch {
            localRepository.getAllBooksStream().collect { result ->
                try {
                    _state.update { BookListState.Success(books = result) }
                } catch (e: Exception) {
                    _state.update { BookListState.Error(exception = e) }
                }
            }

        }
        _state.update { BookListState.Loading(value = false) }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            localRepository.deleteBookById(id)
        }
    }
}
