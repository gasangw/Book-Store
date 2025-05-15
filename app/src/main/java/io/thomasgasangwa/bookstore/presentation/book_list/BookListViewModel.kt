package io.thomasgasangwa.bookstore.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.common.Resource
import io.thomasgasangwa.bookstore.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
): ViewModel()  {

    private val _state = MutableStateFlow<BookListState>(BookListState.Success(emptyList()))

    val state: StateFlow<BookListState> = _state.onStart { getAllBooks() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
        initialValue = BookListState.Success(emptyList())
    )

   private fun getAllBooks() {
       _state.value = BookListState.Loading(value = true)
       viewModelScope.launch {
           val books = bookRepository.getAllBooksStream()

           books.collect {
               _state.value = BookListState.Success(it)
           }

//          _state.value = when (books) {
//              is Resource.Success -> BookListState.Success(books.data ?: emptyList())
//              is Resource.Error -> BookListState.Error(Exception(books.message))
//              else -> {
//
//              }
//          }
           _state.value = BookListState.Loading(value = false)
       }
   }


}

// 1. debbug the code
// 2. Introduce logging

// learn more about flows and implement it.
// introduce logging (logger)