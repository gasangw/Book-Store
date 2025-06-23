package io.thomasgasangwa.bookcollection.presentation.book

import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.thomasgasangwa.bookcollection.common.Constants.DEFAULT_COVER
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
import io.thomasgasangwa.bookcollection.presentation.add_book.AddBookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
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

/// <methodUnderTest>_<precondition>_<expectedResult>()

    @Test
    fun `addBook-formData is all valid-localRepository#insertBook is called only once`() = runTest {
        val bookSlot = slot<Book>()
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

        coEvery { fakeLocalRepository.insertBook(capture(bookSlot)) } returns Result.Success(Unit)

        viewModel = AddBookViewModel(fakeLocalRepository)

        viewModel.onTitleChanged(newBook.title)
        viewModel.onDescriptionChanged(newBook.description)
        viewModel.onReleaseDateChanged(newBook.releaseDate)
        viewModel.onLikesChanged(newBook.likes.toString())
        viewModel.onCoverChanged(newBook.cover)
        viewModel.onPagesChanged(newBook.pages.toString())

        viewModel.addBook()

        testDispatcher.scheduler.advanceUntilIdle()

        val expectedBook = bookSlot.captured
        assertEquals(newBook.title, expectedBook.title)
        assertEquals(newBook.description, expectedBook.description)
        assertEquals(newBook.pages, expectedBook.pages)
        assertEquals(newBook.likes, expectedBook.likes)
        assertEquals(newBook.cover, expectedBook.cover)
        assertEquals(newBook.releaseDate, expectedBook.releaseDate)

        coVerify(exactly = 1) { fakeLocalRepository.insertBook(any()) }

    }

    @Test
    fun `addBook-allValid-coverBlank-usesDefaultCover`() = runTest {
        val bookSlot = slot<Book>()
        coEvery { fakeLocalRepository.insertBook(capture(bookSlot)) } returns Result.Success(Unit)

        viewModel = AddBookViewModel(fakeLocalRepository)
        viewModel.onTitleChanged("Title")
        viewModel.onDescriptionChanged("Desc")
        viewModel.onReleaseDateChanged("2025-06-04")
        viewModel.onPagesChanged("100")
        viewModel.onCoverChanged("")
        viewModel.onLikesChanged("10")

        viewModel.addBook()

        coVerify(exactly = 1) { fakeLocalRepository.insertBook(any()) }
        assertEquals(DEFAULT_COVER, bookSlot.captured.cover)
    }

    @Test
    fun `addBook-formData is not all valid-localRepository#insertBook is not called`() = runTest {
        val newBook = Book(
            id = 0,
            title = "",
            releaseDate = "June 2, 2025",
            description = "",
            pages = 10,
            cover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcjl4Rv6ypjyty5A4hMzfcwvg71Q9LKJDjYg&s",
            likes = 0,
            isFavorite = false
        )

        val bookSlot = slot<Book>()
        coEvery { fakeLocalRepository.insertBook(capture(bookSlot)) } returns Result.Success(Unit)

        viewModel = AddBookViewModel(fakeLocalRepository)

        viewModel.onTitleChanged(newBook.title)
        viewModel.onDescriptionChanged(newBook.description)
        viewModel.onReleaseDateChanged(newBook.releaseDate)
        viewModel.onLikesChanged(newBook.likes.toString())
        viewModel.onCoverChanged(newBook.cover)
        viewModel.onPagesChanged(newBook.pages.toString())

        viewModel.addBook()

        testDispatcher.scheduler.advanceUntilIdle()

        val isBookAdded = viewModel.addBook()

        assertEquals(isBookAdded, false)

        coVerify { fakeLocalRepository wasNot called }

    }
}