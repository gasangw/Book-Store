package io.thomasgasangwa.bookstore.domain.repository

import io.thomasgasangwa.bookstore.data.remote.dto.BookDto

interface BookRespository {

    suspend fun getAllBooks(): List<BookDto>
}