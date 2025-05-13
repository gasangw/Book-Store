package io.thomasgasangwa.bookstore.presentation.viewmodel.booklist_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
): ViewModel()  {

    private val _state: StateFlow<BookListState> = MutableStateFlow<BookListState>(BookListState.Success(emptyList())).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookListState.Success(emptyList())
    )

    val state: StateFlow<BookListState> = _state

   fun getAllBooks() {
       viewModelScope.launch {
           bookRepository.getAllBooksStream().map {  }
       }
   }

}