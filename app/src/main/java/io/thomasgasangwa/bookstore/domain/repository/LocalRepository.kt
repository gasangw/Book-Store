package io.thomasgasangwa.bookstore.domain.repository

import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllBooksStream(): Flow<Result<List<Book>>>
    fun getBookStream(id: Int): Flow<Result<Book>>
    fun getFavoriteBooksStream(): Flow<Result<List<Book>>>
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Result<Unit>
    suspend fun insertBook(book: Book): Result<Unit>
    suspend fun updateBook(book: Book): Result<Unit>
    suspend fun deleteBookById(id: Int): Result<Unit>
    suspend fun updateLikes(id: Int, likes: Int): Result<Unit>
}
