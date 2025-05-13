package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl (
    private val itemDao: BookDao
):  BookRepository {
    override fun getAllBooksStream(): Flow<List<Book>> = itemDao.getAllBooks()
    override fun getBookStream(id: Int): Flow<Book> = itemDao.getBookById(id)
    override suspend fun insertBook(book: Book) = itemDao.insert(book)
    override suspend fun deleteBook(book: Book) = itemDao.delete(book)
    override suspend fun updateBook(book: Book) = itemDao.update(book)
}
