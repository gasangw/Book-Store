package io.thomasgasangwa.bookstore.di

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.database.BookDatabase
import io.thomasgasangwa.bookstore.data.repository.BookRepositoryImpl
import io.thomasgasangwa.bookstore.domain.repository.BookRepository

import io.thomasgasangwa.bookstore.presentation.book_list.BookListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bookModule = module {

   single { BookDatabase.getDatabase(get())}

    single<BookDao> { get<BookDatabase>().bookDao() }

    single<BookRepository> { BookRepositoryImpl(get()) }


     viewModel { BookListViewModel(get()) }
}
