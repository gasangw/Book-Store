package io.thomasgasangwa.bookstore.presentation.viewmodel.BookListViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.data.repository.BookRepository
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.use_case.get_books.GetBooksUseCase
import io.thomasgasangwa.bookstore.presentation.viewmodel.BookListViewModel.BookListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val bookRepository: BookRepository
): ViewModel()  {
    private val _state = MutableStateFlow<BookListState>(BookListState())
    val state = _state.asStateFlow()


    init {
        getBooks()
    }

    private fun getBooks() {

            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                val result: Result<List<Book>, Exception> = getBooksUseCase()

                _state.update { it.copy(isLoading = false) }

                when(result) {
                    is Result.Success -> {
                        _state.update { it.copy(books = result.data) }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(error = result.error.message ?: "An unexpected error occurred") }
                    }
                }



            }
    }

}