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

    fun addBook():Boolean {
        val state = _formState.value
        if (state.title.isNotEmpty() &&
            state.description.isNotEmpty() &&
            state.releaseDate.isNotEmpty() &&
            state.pages > 0 &&
            state.likes >= 0
        ) {
            val book = Book(
                id = 0,
                title = state.title,
                description = state.description,
                releaseDate = state.releaseDate,
                pages = state.pages,
                cover = if (state.cover.isBlank()) DEFAULT_COVER else state.cover,
                likes = state.likes,
                isFavorite = false
            )
            viewModelScope.launch {
                localRepository.insertBook(book)
            }
          return true
        }
        return false
    }

}
/// seven unit tests for this method.
// 1. if the book contains the values you provided and also the if else for the cover to be tested (2 test)
// 2. test that the localRepository is actually called with the book class
// 3. if any of the if conditions is false, you do not call the LocalRepository.insertBook. (find out the way
// to get the arguments)--> check the call argument for insertBook. (Mockito library how to verify arguments)

// for the else case make one of the values empty or false .
// improve the naming of test methods ( this is a template <methodUnderTest>_<precondition>_<expectedResult>() )