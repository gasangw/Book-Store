package io.thomasgasangwa.bookstore.di

import androidx.room.Room
import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.database.BookDatabase
import io.thomasgasangwa.bookstore.data.repository.BookRepositoryImpl
import io.thomasgasangwa.bookstore.data.repository.BookRepository

import io.thomasgasangwa.bookstore.presentation.viewmodel.booklist_viewmodel.BookListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bookModule = module {


    single {
        Room.databaseBuilder(
            androidContext(),
            BookDatabase::class.java,
            "book_database"
        ).fallbackToDestructiveMigrationOnDowngrade(true).build()
    }

    single<BookDao> { get<BookDatabase>().bookDao() }

    single<BookRepository> { BookRepositoryImpl(get()) }


     viewModel { BookListViewModel(get()) }
}
