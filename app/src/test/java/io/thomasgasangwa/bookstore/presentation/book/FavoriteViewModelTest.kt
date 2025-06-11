package io.thomasgasangwa.bookstore.presentation.book

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
import io.thomasgasangwa.bookcollection.presentation.favorites.FavoriteBookState
import io.thomasgasangwa.bookcollection.presentation.favorites.FavoriteViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: FavoriteViewModel
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

    val favoriteBooks = listOf(
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

    @Test
    fun `getFavoriteBooks-repository is working perfectly-fakeLocalRepository#getFavoriteBooksStream is called once`() =
        runTest {
            coEvery { fakeLocalRepository.getFavoriteBooksStream() } returns flowOf(
                Result.Success(
                    favoriteBooks
                )
            )

            viewModel = FavoriteViewModel(fakeLocalRepository)

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect {}
            }
            testDispatcher.scheduler.advanceUntilIdle()

            val favoriteBooks = (viewModel.state.value is FavoriteBookState.Success)

            assertEquals(favoriteBooks, favoriteBooks)

            coVerify(exactly = 1) { fakeLocalRepository.getFavoriteBooksStream() }
        }

    @Test
    fun `updateFavoriteStatus-given a book with isFavorite true-fakeRepository#updateFavoriteStatus is called once`() =
        runTest {
            val bookId = slot<Int>()
            val isFavorite = slot<Boolean>()

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

            coEvery {
                fakeLocalRepository.updateFavoriteStatus(
                    capture(bookId),
                    capture(isFavorite)
                )
            } returns Result.Success(Unit)

            coEvery { fakeLocalRepository.getFavoriteBooksStream() } returns flowOf(
                Result.Success(
                    emptyList()
                )
            )

            viewModel = FavoriteViewModel(fakeLocalRepository)

            viewModel.updateFavoriteStatus(book.id, !book.isFavorite)

            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect {}
            }

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(bookId.captured, book.id)
            assertEquals(isFavorite.captured, !book.isFavorite)

            coVerify(exactly = 1) { fakeLocalRepository.updateFavoriteStatus(any(), any()) }

        }
}