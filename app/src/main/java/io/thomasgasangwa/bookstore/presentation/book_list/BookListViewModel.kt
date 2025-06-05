package io.thomasgasangwa.bookstore.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.domain.usecase.GetAllBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val localRepository: LocalRepository,
    private val getAllBooksUseCase: GetAllBooksUseCase
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
            getAllBooksUseCase()
            localRepository.getAllBooksStream().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { BookListState.Success(books = result.value) }
                    }

                    is Result.Failure -> {
                        _state.update { BookListState.Error(exception = result.exception as Exception) }
                    }
                }
            }

        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            localRepository.deleteBookById(id)
        }
    }

    fun updateLikes(id: Int, likes: Int, nowLiked: Boolean) {
        if (nowLiked) {
            viewModelScope.launch {
                localRepository.updateLikes(id, likes + 1)
            }
        } else {
            if (likes == 0) return
            viewModelScope.launch {
                localRepository.updateLikes(id, likes - 1)
            }
        }

    }

}
