package io.thomasgasangwa.bookstore.presentation.book_details

import androidx.lifecycle.ViewModel
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookDetailsViewModel(
    private val bookId: Int,
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow<String>("")
    val state: StateFlow<String> = _state
}