package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.remote.BookApiService
import io.thomasgasangwa.bookstore.data.remote.toBook
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository

class RemoteRepositoryImpl(
    private val bookApiService: BookApiService
): RemoteRepository {
    override suspend fun getRemoteBooks(): List<Book> = bookApiService.getBooks().map { it.toBook() }
}