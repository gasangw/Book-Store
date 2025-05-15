package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.mapper.toBook
import io.thomasgasangwa.bookstore.data.local.mapper.toBookEntity
import io.thomasgasangwa.bookstore.domain.repository.BookRepository
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.data.local.mapper.toBookList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl (
    private val itemDao: BookDao
): BookRepository {
    override fun getAllBooksStream(): Flow<List<Book>> =  itemDao.getAllBooks().map { it.toBookList() }
    override fun getBookStream(id: Int): Flow<Book> = itemDao.getBookById(id).map { it.toBook() }
    override suspend fun insertBook(book: Book) = itemDao.insert(book.toBookEntity())
    override suspend fun deleteBookById(id: Int) = itemDao.deleteBookById(id)
    override suspend fun updateBook(book: Book) = itemDao.update(book.toBookEntity())
}

