package io.thomasgasangwa.bookstore.di

import androidx.lifecycle.viewmodel.compose.viewModel
import io.thomasgasangwa.bookstore.common.Constants.BASE_URL
import io.thomasgasangwa.bookstore.data.remote.BooksApi
import io.thomasgasangwa.bookstore.data.repository.BookRepositoryImpl
import io.thomasgasangwa.bookstore.domain.repository.BookRespository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val bookModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(BooksApi::class.java)
    }

    single<BookRespository> { BookRepositoryImpl(get()) }

   //viewModel {  }
}