package io.thomasgasangwa.bookstore.presentation.view_models

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.presentation.update_book.UpdateBookViewModel
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
import kotlin.test.assertNotSame

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateBookViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository

    private lateinit var viewModel: UpdateBookViewModel
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

    @Test
    fun `updateBook-all fields are valid-fakeRepository#updateBook is called once`() = runTest {
        val updateBookSlot = slot<Book>()

        val originalBook = Book(
            id = 2,
            title = "business2",
            releaseDate = "May 2, 1973",
            description = "Learn how to start a business with less capital",
            pages = 900,
            cover = "",
            likes = 90,
            isFavorite = false
        )

        coEvery { fakeLocalRepository.updateBook(capture(updateBookSlot)) } returns Result.Success(
            Unit
        )

        viewModel = UpdateBookViewModel(originalBook, fakeLocalRepository)

        viewModel.getCurrentBook()
        viewModel.updateTitle("idea 4")
        viewModel.updateDescription("Learn how to start a business with less capital")
        viewModel.updateCover("")
        viewModel.updatePages("1000")
        viewModel.updateLikes("120")
        viewModel.updateReleaseDate("May 2, 2023")

        viewModel.updateBook()

        testDispatcher.scheduler.advanceUntilIdle()

        assertNotSame(originalBook, updateBookSlot.captured)
        assertEquals(originalBook.id, updateBookSlot.captured.id)

        coVerify { fakeLocalRepository.updateBook(any()) }
    }
}