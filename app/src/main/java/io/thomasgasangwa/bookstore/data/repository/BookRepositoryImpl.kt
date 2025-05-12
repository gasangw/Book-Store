package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.remote.BooksApi
import io.thomasgasangwa.bookstore.data.remote.dto.BookDto
import io.thomasgasangwa.bookstore.domain.repository.BookRespository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BooksApi
):  BookRespository{
    override suspend fun getAllBooks(): List<BookDto> {
        return api.getAllBooks()
    }
}
