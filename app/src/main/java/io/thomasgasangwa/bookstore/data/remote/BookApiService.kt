package io.thomasgasangwa.bookstore.data.remote

import io.thomasgasangwa.bookstore.common.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    fun create(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

interface BookApiService {
    @GET("/en/books")
    suspend fun getBooks(): List<BookDto>
}