package io.thomasgasangwa.bookstore.presentation.view_models

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.presentation.favorites.FavoriteBookState
import io.thomasgasangwa.bookstore.presentation.favorites.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertNotEquals

class FavoriteViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var testDispatcher: TestDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    val books = listOf(
        Book(
            id = 1,
            title = "business",
            releaseDate = "May 23, 1993",
            description = "Learn how to start a business",
            pages = 22,
            cover = "",
            likes = 30,
            isFavorite = true
        ),
        Book(
            id = 2,
            title = "business2",
            releaseDate = "May 2, 1973",
            description = "Learn how to start a business with less capital",
            pages = 900,
            cover = "",
            likes = 90,
            isFavorite = true
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get all favorite books`() = runTest {
        coEvery { fakeLocalRepository.getFavoriteBooksStream() } returns flowOf(Result.Success(books))

        viewModel = FavoriteViewModel(fakeLocalRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { fakeLocalRepository.getFavoriteBooksStream() }

        val actualBooks = viewModel.state.value
        assert(actualBooks is FavoriteBookState.Success)
        assertEquals(books, (actualBooks as FavoriteBookState.Success).books)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `update the favorite status of a book`() = runTest {
        val book = Book(
            id = 1,
            title = "business",
            releaseDate = "May 23, 1993",
            description = "Learn how to start a business",
            pages = 22,
            cover = "",
            likes = 30,
            isFavorite = true
        )
        coEvery {
            fakeLocalRepository.updateFavoriteStatus(
                book.id,
                !book.isFavorite
            )
        } returns Result.Success(Unit)

        val updatedBooks: List<Book> = books.map { it ->
            if (it.id == book.id) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }

        coEvery { fakeLocalRepository.getFavoriteBooksStream() } returns flowOf(
            Result.Success(
                updatedBooks
            )
        )
        viewModel = FavoriteViewModel(fakeLocalRepository)

        viewModel.updateFavoriteStatus(book.id, !book.isFavorite)
        viewModel.getFavoriteBooks()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { fakeLocalRepository.updateFavoriteStatus(id = book.id, isFavorite = false) }
        coVerify { fakeLocalRepository.getFavoriteBooksStream() }

        val updatedBook = viewModel.state.value

        assertNotEquals(
            book.isFavorite,
            (updatedBook as FavoriteBookState.Success).books[0].isFavorite
        )

    }
}