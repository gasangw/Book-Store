package io.thomasgasangwa.bookstore.data.remote

import io.thomasgasangwa.bookstore.data.remote.dto.BookDto
import retrofit2.http.GET

interface BooksApi {
    @GET("/books")
    suspend fun getAllBooks(): List<BookDto>
}