package io.thomasgasangwa.bookcollection.data.repository

import io.thomasgasangwa.bookcollection.data.remote.BookApiService
import io.thomasgasangwa.bookcollection.data.remote.toBook
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.repository.RemoteRepository

class RemoteRepositoryImpl(
    private val bookApiService: BookApiService
) : RemoteRepository {
    override suspend fun getRemoteBooks(): List<Book> =
        bookApiService.getBooks().map { it.toBook() }
}