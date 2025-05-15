package io.thomasgasangwa.bookstore.di

import io.thomasgasangwa.bookstore.common.Constants.BASE_URL
import io.thomasgasangwa.bookstore.data.remote.BookApiService
import io.thomasgasangwa.bookstore.data.repository.RemoteRepositoryImpl
import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteBookModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(BookApiService::class.java) }

    single<RemoteRepository> { RemoteRepositoryImpl(get())}
}