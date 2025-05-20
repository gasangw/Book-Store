package io.thomasgasangwa.bookstore.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookId: Int,
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow<BookDetailsState>(BookDetailsState.Loading(value = false))
    val state: StateFlow<BookDetailsState> = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookDetailsState.Loading()
    )

    init {
        fetchBook()
    }

    fun fetchBook() {
        _state.value = BookDetailsState.Loading(value = true)
        viewModelScope.launch {
            try {
                localRepository.getBookStream(bookId).collect { book ->
                    _state.value = BookDetailsState.Success(book)
                }
            } catch (e: Exception) {
                _state.value = BookDetailsState.Error(e)
            }
        }
        _state.value = BookDetailsState.Loading(value = false)

    }
}