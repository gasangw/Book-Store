package io.thomasgasangwa.bookstore.presentation.book_list

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

        localRepository.getAllBooksStream().map { bookList ->
            BookListState.Success(bookList) as BookListState

        }.onStart {
            _state.value = BookListState.Loading(value = true)
        }.onEach { bookListState ->
            _state.value = bookListState
        }.onCompletion {
            _state.value = BookListState.Loading(value = false)
        }.catch { cause ->
            _state.value = BookListState.Error(cause as Exception)
        }.launchIn(viewModelScope)

        //this is method two.
//        _state.update { BookListState.Loading(value = true) }
//        viewModelScope.launch {
//            localRepository.getAllBooksStream().collect { result ->
//                try {
//                    _state.update { BookListState.Success(books = result) }
//                } catch (e: Exception) {
//                    _state.update { BookListState.Error(exception = e) }
//                }
//            }
//
//        }
//        _state.update { BookListState.Loading(value = false) }
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
