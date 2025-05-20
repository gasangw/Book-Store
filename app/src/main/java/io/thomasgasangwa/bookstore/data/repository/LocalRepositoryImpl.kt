package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.mapper.toBook
import io.thomasgasangwa.bookstore.data.local.mapper.toBookEntity
import io.thomasgasangwa.bookstore.data.local.mapper.toBookList
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepositoryImpl(
    private val itemDao: BookDao
) : LocalRepository {
    override fun getAllBooksStream(): Flow<List<Book>> =
        itemDao.getAllBooks().map { it.toBookList() }

    override fun getBookStream(id: Int): Flow<Book> = itemDao.getBookById(id).map { it.toBook() }
    override fun getFavoriteBooksStream(): Flow<List<Book>> =
        itemDao.getFavoriteBooks().map { it.toBookList() }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) =
        itemDao.updateFavoriteStatus(id, isFavorite)

    override suspend fun insertBook(book: Book) = itemDao.insert(book.toBookEntity())
    override suspend fun deleteBookById(id: Int) = itemDao.deleteBookById(id)
    override suspend fun updateBook(book: Book) = itemDao.update(book.toBookEntity())
    override suspend fun updateLikes(id: Int, likes: Int) = itemDao.updateLikes(id, likes)
}


