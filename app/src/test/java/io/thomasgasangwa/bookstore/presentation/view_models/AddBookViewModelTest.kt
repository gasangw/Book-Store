package io.thomasgasangwa.bookstore.presentation.view_models

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.presentation.add_book.AddBookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertContains

class AddBookViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: AddBookViewModel
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    val books = mutableListOf(
        Book(
            id = 1,
            title = "business",
            releaseDate = "May 23, 1993",
            description = "Learn how to start a business",
            pages = 22,
            cover = "",
            likes = 30,
            isFavorite = false
        ),
        Book(
            id = 2,
            title = "business2",
            releaseDate = "May 2, 1973",
            description = "Learn how to start a business with less capital",
            pages = 900,
            cover = "",
            likes = 90,
            isFavorite = false
        )
    )

    @Test
    fun `add a new book`() = runTest {
        val newBook = Book(
            id = 0,
            title = "i am new",
            releaseDate = "June 2, 2025",
            description = "This is a new book being added",
            pages = 10,
            cover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcjl4Rv6ypjyty5A4hMzfcwvg71Q9LKJDjYg&s",
            likes = 0,
            isFavorite = false
        )

        coEvery { fakeLocalRepository.insertBook(newBook) } returns Result.Success(Unit)

        viewModel = AddBookViewModel(fakeLocalRepository)

        viewModel.onTitleChanged(newBook.title)
        viewModel.onDescriptionChanged(newBook.description)
        viewModel.onReleaseDateChanged(newBook.releaseDate)
        viewModel.onLikesChanged(newBook.likes.toString())
        viewModel.onCoverChanged(newBook.cover)
        viewModel.onPagesChanged(newBook.pages.toString())

        viewModel.addBook()
        books.add(newBook)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { fakeLocalRepository.insertBook(newBook) }

        assertContains(books, newBook)

    }
}