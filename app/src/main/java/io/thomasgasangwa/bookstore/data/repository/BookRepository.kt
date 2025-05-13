package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
     fun getAllBooksStream(): Flow<List<Book>>
     fun getBookStream(id: Int): Flow<Book>
     suspend fun insertBook(book: Book)
     suspend fun updateBook(book: Book)
     suspend fun deleteBook(book: Book)
}