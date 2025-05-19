package io.thomasgasangwa.bookstore.di

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.database.BookDatabase
import io.thomasgasangwa.bookstore.data.repository.LocalRepositoryImpl
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.presentation.add_book.AddBookViewModel

import io.thomasgasangwa.bookstore.presentation.book_list.BookListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val localBookModule = module {

//    single { RetrofitClient.create() }
//
//    single { get<Retrofit>().create(BookApiService::class.java) }
//
//    single<RemoteRepository> { RemoteRepositoryImpl(get()) }

    //   single { FetchBooksUseCase(get(), get()) }

    single { BookDatabase.getDatabase(get()) }

    single<BookDao> { get<BookDatabase>().bookDao() }

    single<LocalRepository> { LocalRepositoryImpl(get()) }

    viewModel { BookListViewModel(get()) }
    viewModel { AddBookViewModel(get()) }
}
