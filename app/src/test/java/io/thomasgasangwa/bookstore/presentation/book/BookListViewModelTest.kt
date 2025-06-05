package io.thomasgasangwa.bookstore.presentation.book

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import io.thomasgasangwa.bookstore.common.RepositoryException
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.domain.usecase.GetAllBooksUseCase
import io.thomasgasangwa.bookstore.presentation.book_list.BookListState
import io.thomasgasangwa.bookstore.presentation.book_list.BookListViewModel
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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BookListViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    @MockK
    lateinit var getAllBooksUseCase: GetAllBooksUseCase

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

//    val books = listOf(
//        Book(
//            id = 1,
//            title = "business",
//            releaseDate = "May 23, 1993",
//            description = "Learn how to start a business",
//            pages = 22,
//            cover = "",
//            likes = 30,
//            isFavorite = false
//        ),
//        Book(
//            id = 2,
//            title = "business2",
//            releaseDate = "May 2, 1973",
//            description = "Learn how to start a business with less capital",
//            pages = 900,
//            cover = "",
//            likes = 90,
//            isFavorite = false
//        )
//    )


    val book = Book(
        id = 1,
        title = "business",
        releaseDate = "May 23, 1993",
        description = "Learn how to start a business",
        pages = 22,
        cover = "",
        likes = 30,
        isFavorite = false
    )

    /// <methodUnderTest>_<precondition>_<expectedResult>()

    @Test
    fun `getAllBooks-repository is working perfectly-fakeLocalRepository#getAllBooksStream is called once`() =
        runTest {
            coEvery { getAllBooksUseCase(); fakeLocalRepository.getAllBooksStream() } returns flowOf(
                Result.Success(emptyList())
            )
            viewModel = BookListViewModel(fakeLocalRepository, getAllBooksUseCase)

            val listOfBooks = slot<List<BookListState.Success>>()

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect {}
            }

            listOfBooks.captured.let { books ->
                assertEquals(books, (viewModel.state.value as BookListState.Success).books)
                assertNotNull(books)
                assert(books.isEmpty())
            }
            verify(exactly = 1) { fakeLocalRepository.getAllBooksStream() }
        }

    @Test
    fun `getAllBooks-an error in the repository-fakeLocalRepository#getAllBooksStream is not called`() =
        runTest {
            coEvery { getAllBooksUseCase(); fakeLocalRepository.getAllBooksStream() } returns flowOf(
                Result.Failure(
                    RepositoryException.DatabaseException("Database Exception, failed to getAllBooks")
                )
            )
            viewModel = BookListViewModel(fakeLocalRepository, getAllBooksUseCase)

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect {}
            }
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertTrue(state is BookListState.Error)

            assertEquals("Database Exception, failed to getAllBooks", state.exception.message)

            verify { fakeLocalRepository.getAllBooksStream() }
        }

//    @Test
//    fun `delete a book with a given Id`() = runTest {
//
//        coEvery { fakeLocalRepository.deleteBookById(1) } returns Result.Success(Unit)
//        coEvery { getAllBooksUseCase(); fakeLocalRepository.getAllBooksStream() } returns flowOf(
//            Result.Success(books.filter { it.id != 1 })
//        )
//
//        viewModel = BookListViewModel(fakeLocalRepository, getAllBooksUseCase)
//
//        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
//            viewModel.state.collect {}
//        }
//        viewModel.deleteBook(1)
//
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        val actualBooks = viewModel.state.value
//        val bookList = (actualBooks as BookListState.Success).books
//        assert(!bookList.any { it.id == 1 })
//
//    }
}
