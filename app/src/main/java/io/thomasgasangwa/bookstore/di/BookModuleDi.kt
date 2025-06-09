package io.thomasgasangwa.bookstore.di

import androidx.credentials.CredentialManager
import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.database.BookDatabase
import io.thomasgasangwa.bookstore.data.remote.BookApiService
import io.thomasgasangwa.bookstore.data.remote.RetrofitClient
import io.thomasgasangwa.bookstore.data.repository.AuthRepositoryImpl
import io.thomasgasangwa.bookstore.data.repository.LocalRepositoryImpl
import io.thomasgasangwa.bookstore.data.repository.RemoteRepositoryImpl
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.AuthRepository
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository
import io.thomasgasangwa.bookstore.domain.usecase.GetAllBooksUseCase
import io.thomasgasangwa.bookstore.presentation.add_book.AddBookViewModel
import io.thomasgasangwa.bookstore.presentation.book_details.BookDetailsViewModel
import io.thomasgasangwa.bookstore.presentation.book_list.BookListViewModel
import io.thomasgasangwa.bookstore.presentation.favorites.FavoriteViewModel
import io.thomasgasangwa.bookstore.presentation.update_book.UpdateBookViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val localBookModule = module {

    single { CredentialManager.create(androidContext()) }

    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single { RetrofitClient.create() }

    single { get<Retrofit>().create(BookApiService::class.java) }

    single<RemoteRepository> { RemoteRepositoryImpl(get()) }

    single { GetAllBooksUseCase(get(), get()) }

    single { BookDatabase.getDatabase(get()) }

    single<BookDao> { get<BookDatabase>().bookDao() }

    single<LocalRepository> { LocalRepositoryImpl(get()) }

    viewModel { BookListViewModel(get(), get()) }
    viewModel { AddBookViewModel(get()) }
    viewModel { (bookId: Int) -> BookDetailsViewModel(bookId, get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { (book: Book) -> UpdateBookViewModel(book, get()) }
}
