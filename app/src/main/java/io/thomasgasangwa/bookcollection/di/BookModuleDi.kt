package io.thomasgasangwa.bookcollection.di

import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import io.thomasgasangwa.bookcollection.common.state.UserStateHolderImpl
import io.thomasgasangwa.bookcollection.data.local.dao.BookDao
import io.thomasgasangwa.bookcollection.data.local.dao.BookingDao
import io.thomasgasangwa.bookcollection.data.local.database.BookDatabase
import io.thomasgasangwa.bookcollection.data.remote.BookApiService
import io.thomasgasangwa.bookcollection.data.remote.RetrofitClient
import io.thomasgasangwa.bookcollection.data.repository.AuthRepositoryImpl
import io.thomasgasangwa.bookcollection.data.repository.BookingRepositoryImpl
import io.thomasgasangwa.bookcollection.data.repository.LocalRepositoryImpl
import io.thomasgasangwa.bookcollection.data.repository.RemoteRepositoryImpl
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import io.thomasgasangwa.bookcollection.domain.repository.BookingRepository
import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
import io.thomasgasangwa.bookcollection.domain.repository.RemoteRepository
import io.thomasgasangwa.bookcollection.domain.usecase.GetAllBooksUseCase
import io.thomasgasangwa.bookcollection.globalstate.UserStateHolder
import io.thomasgasangwa.bookcollection.presentation.add_book.AddBookViewModel
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInViewModel
import io.thomasgasangwa.bookcollection.presentation.auth.sign_up.SignUpViewModel
import io.thomasgasangwa.bookcollection.presentation.book_details.BookDetailsViewModel
import io.thomasgasangwa.bookcollection.presentation.book_list.BookListViewModel
import io.thomasgasangwa.bookcollection.presentation.favorites.FavoriteViewModel
import io.thomasgasangwa.bookcollection.presentation.update_book.UpdateBookViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val localBookModule = module {

    single { CredentialManager.create(androidContext()) }
    single { FirebaseAuth.getInstance() }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { RetrofitClient.create() }

    single { get<Retrofit>().create(BookApiService::class.java) }

    single<RemoteRepository> { RemoteRepositoryImpl(get()) }

    single { GetAllBooksUseCase(get(), get()) }

    single { BookDatabase.getDatabase(get()) }

    single<BookDao> { get<BookDatabase>().bookDao() }
    single<BookingDao> { get<BookDatabase>().bookingDao() }

    single<LocalRepository> { LocalRepositoryImpl(get()) }

    single<BookingRepository> { BookingRepositoryImpl(get()) }

    single<UserStateHolder> { UserStateHolderImpl() }

    viewModel { BookListViewModel(get(), get()) }
    viewModel { AddBookViewModel(get()) }
    viewModel { (bookId: Int) -> BookDetailsViewModel(bookId, get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { (book: Book) -> UpdateBookViewModel(book, get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
}
