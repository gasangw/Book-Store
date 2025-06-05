//package io.thomasgasangwa.bookstore.presentation.view_models
//
//import io.mockk.MockKAnnotations
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.MockK
//import io.mockk.slot
//import io.thomasgasangwa.bookstore.common.Result
//import io.thomasgasangwa.bookstore.domain.model.Book
//import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
//import io.thomasgasangwa.bookstore.presentation.update_book.UpdateBookViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestDispatcher
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertNotSame
//
//class UpdateBookViewModelTest {
//    @MockK
//    lateinit var fakeLocalRepository: LocalRepository
//
//    private lateinit var viewModel: UpdateBookViewModel
//    private lateinit var testDispatcher: TestDispatcher
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setup() {
//        MockKAnnotations.init(this)
//        testDispatcher = UnconfinedTestDispatcher()
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `update the existing book`() = runTest {
//        val updateBookSlot = slot<Book>()
//
//        val originalBook = Book(
//            id = 2,
//            title = "business2",
//            releaseDate = "May 2, 1973",
//            description = "Learn how to start a business with less capital",
//            pages = 900,
//            cover = "",
//            likes = 90,
//            isFavorite = false
//        )
////        val updatedBook = Book(
////            id = 2,
////            title = "idea 4",
////            releaseDate = "May 2, 2023",
////            description = "Learn how to start a business with less capital",
////            pages = 1000,
////            cover = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcjl4Rv6ypjyty5A4hMzfcwvg71Q9LKJDjYg&s",
////            likes = 390,
////            isFavorite = false
////        )
//
//        coEvery { fakeLocalRepository.updateBook(capture(updateBookSlot)) } returns Result.Success(
//            Unit
//        )
//
//        viewModel = UpdateBookViewModel(originalBook, fakeLocalRepository)
//
//        viewModel.getCurrentBook()
//        viewModel.updateTitle(updatedBook.title)
//        viewModel.updateDescription(updatedBook.description)
//        viewModel.updateCover(updatedBook.cover)
//        viewModel.updatePages(updatedBook.pages.toString())
//        viewModel.updateLikes(updatedBook.likes.toString())
//        viewModel.updateReleaseDate(updatedBook.releaseDate)
//
//        viewModel.updateBook()
//
//        coVerify { fakeLocalRepository.updateBook(any()) }
//
//        assertNotSame(originalBook, updatedBook)
//        assertEquals(originalBook.id, updatedBook.id)
//    }
//}