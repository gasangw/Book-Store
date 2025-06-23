package io.thomasgasangwa.bookcollection.domain.repository

import io.thomasgasangwa.bookcollection.domain.model.Book


interface RemoteRepository {
    suspend fun getRemoteBooks(): List<Book>
}