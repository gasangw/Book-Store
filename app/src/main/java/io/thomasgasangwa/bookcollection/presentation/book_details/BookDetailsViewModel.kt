package io.thomasgasangwa.bookcollection.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
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
            localRepository.getBookStream(bookId).collect { book ->
                when (book) {
                    is Result.Success -> {
                        _state.value = BookDetailsState.Success(book.value)
                    }

                    is Result.Failure -> {
                        _state.value = BookDetailsState.Error(book.exception as Exception)
                    }
                }
            }
        }
    }
}