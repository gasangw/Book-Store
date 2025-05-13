package io.thomasgasangwa.bookstore.di

import io.thomasgasangwa.bookstore.common.Constants.BASE_URL
import io.thomasgasangwa.bookstore.data.remote.BooksApi
import io.thomasgasangwa.bookstore.data.repository.BookRepositoryImpl
import io.thomasgasangwa.bookstore.domain.repository.BookRespository
import io.thomasgasangwa.bookstore.domain.use_case.get_books.GetBooksUseCase
import io.thomasgasangwa.bookstore.presentation.book_list.BookListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.EmptyCoroutineContext.get

val bookModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(BooksApi::class.java)
    }

    single<BookRespository> { BookRepositoryImpl(get()) }

    single { GetBooksUseCase(get()) }

     viewModel { BookListViewModel(get()) }
}