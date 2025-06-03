package io.thomasgasangwa.bookstore.presentation.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookstore.common.Constants.DEFAULT_COVER
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddBookViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _formState = MutableStateFlow<AddBookFormState>(AddBookFormState())

    val formState: StateFlow<AddBookFormState> = _formState.asStateFlow()

    fun onTitleChanged(title: String) {
        _formState.value = _formState.value.copy(title = title)
    }

    fun onDescriptionChanged(description: String) {
        _formState.value = _formState.value.copy(description = description)
    }

    fun onReleaseDateChanged(releaseDate: String) {
        _formState.value = _formState.value.copy(releaseDate = releaseDate)
    }

    fun onPagesChanged(pages: String) {
        val pages = pages.toIntOrNull() ?: 0
        _formState.value = _formState.value.copy(pages = pages)
    }

    fun onCoverChanged(coverUrl: String) {
        _formState.value = _formState.value.copy(cover = coverUrl)
    }

    fun onLikesChanged(likes: String?) {
        val likes = likes?.toIntOrNull() ?: 0
        _formState.value = _formState.value.copy(likes = likes)
    }

    fun addBook() {

        if (_formState.value.title.isNotEmpty() &&
            _formState.value.description.isNotEmpty() &&
            _formState.value.releaseDate.isNotEmpty() &&
            _formState.value.pages > 0 &&
            _formState.value.likes >= 0
        ) {
            val book = Book(
                id = 0,
                title = _formState.value.title,
                description = _formState.value.description,
                releaseDate = _formState.value.releaseDate,
                pages = _formState.value.pages,
                cover = if (_formState.value.cover.isBlank()) DEFAULT_COVER else _formState.value.cover,
                likes = _formState.value.likes,
                isFavorite = false
            )

            viewModelScope.launch {
                localRepository.insertBook(book)
            }

        }
    }

}

