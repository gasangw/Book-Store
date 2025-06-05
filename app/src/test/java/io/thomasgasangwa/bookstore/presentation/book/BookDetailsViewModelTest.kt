package io.thomasgasangwa.bookstore.presentation.book

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.thomasgasangwa.bookstore.common.RepositoryException
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.presentation.book_details.BookDetailsState
import io.thomasgasangwa.bookstore.presentation.book_details.BookDetailsViewModel
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
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BookDetailsViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: BookDetailsViewModel

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

    /// <methodUnderTest>_<precondition>_<expectedResult>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchBook-book id is valid-localRepository#getBookStream is called once`() = runTest {
        val bookId = slot<Int>()
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

        coEvery { fakeLocalRepository.getBookStream(capture(bookId)) } returns flowOf(
            Result.Success(
                book
            )
        )

        viewModel = BookDetailsViewModel(1, fakeLocalRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }

        testDispatcher.scheduler.advanceUntilIdle()

        val bookDetails = (viewModel.state.value as BookDetailsState.Success).book

        assertEquals(bookDetails.id, bookId.captured)

        coVerify(exactly = 1) { fakeLocalRepository.getBookStream(any()) }

    }

    @Test
    fun `fetchBook-book id is not valid-localRepository#getBookStream throws NotFoundException`() =
        runTest {
            val bookId = slot<Int>()

            coEvery { fakeLocalRepository.getBookStream(capture(bookId)) } returns flowOf(
                Result.Failure(RepositoryException.NotFoundException("Book with id 90 is not found"))
            )

            viewModel = BookDetailsViewModel(90, fakeLocalRepository)

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect {}
            }

            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            val errorState = state as BookDetailsState.Error


            assertTrue(state is BookDetailsState.Error)

            assertEquals("Book with id 90 is not found", errorState.exception.message)

            coVerify(exactly = 1) { fakeLocalRepository.getBookStream(any()) }

        }

}