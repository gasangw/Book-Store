package io.thomasgasangwa.bookstore.presentation.book_details

import androidx.lifecycle.ViewModel
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository

class BookDetailsViewModel(
    private val localRepository: LocalRepository
) : ViewModel()