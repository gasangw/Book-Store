package io.thomasgasangwa.bookstore.di

import androidx.room.Room
import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.database.BookDatabase
import io.thomasgasangwa.bookstore.data.repository.BookRepositoryImpl
import io.thomasgasangwa.bookstore.data.repository.BookRepository
//import io.thomasgasangwa.bookstore.domain.use_case.get_books.GetBooksUseCase
import io.thomasgasangwa.bookstore.presentation.viewmodel.BookListViewModel.BookListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory

val bookModule = module {

//    single {
//        Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(BooksApi::class.java)
//    }

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
