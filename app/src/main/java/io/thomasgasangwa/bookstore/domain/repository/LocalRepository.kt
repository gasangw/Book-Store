package io.thomasgasangwa.bookstore.domain.repository

import io.thomasgasangwa.bookstore.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllBooksStream(): Flow<List<Book>>
    fun getBookStream(id: Int): Flow<Book>
    suspend fun insertBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBookById(id: Int)
}