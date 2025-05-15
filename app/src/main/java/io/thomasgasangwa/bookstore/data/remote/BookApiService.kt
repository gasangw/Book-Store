package io.thomasgasangwa.bookstore.data.remote

import retrofit2.http.GET

interface BookApiService {

    @GET("/books")
    suspend fun getBooks(): List<BookDto>
}