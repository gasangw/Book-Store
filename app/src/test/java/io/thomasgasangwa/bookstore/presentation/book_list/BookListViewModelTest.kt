package io.thomasgasangwa.bookstore.presentation.book_list

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.thomasgasangwa.bookstore.common.RepositoryException
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
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
import org.junit.Test

class BookListViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: BookListViewModel

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getAllBooks returns all books in the database`() = runTest {
        coEvery { fakeLocalRepository.getAllBooksStream() } returns flowOf(Result.Success(books))
        viewModel = BookListViewModel(fakeLocalRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        testDispatcher.scheduler.advanceUntilIdle()
        val actualBooks = viewModel.state.value
        assert(actualBooks is BookListState.Success)
        assertEquals(books, (actualBooks as BookListState.Success).books)
    }

    @Test
    fun `throws an error on database failure`() = runTest {
        coEvery { fakeLocalRepository.getAllBooksStream() } returns flowOf(
            Result.Failure(
                RepositoryException.DatabaseException("Database Exception, failed to getAllBooks")
            )
        )
        viewModel = BookListViewModel(fakeLocalRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        testDispatcher.scheduler.advanceUntilIdle()
        val error = viewModel.state.value
        assert(error is BookListState.Error)
    }

    @Test
    fun `delete a book with a given Id`() = runTest {

        coEvery { fakeLocalRepository.deleteBookById(1) } returns Result.Success(Unit)
        coEvery { fakeLocalRepository.getAllBooksStream() } returns flowOf(Result.Success(books.filter { it.id != 1 }))

        viewModel = BookListViewModel(fakeLocalRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        viewModel.deleteBook(1)

        testDispatcher.scheduler.advanceUntilIdle()

        val actualBooks = viewModel.state.value
        val bookList = (actualBooks as BookListState.Success).books
        assert(!bookList.any { it.id == 1 })

    }


}
