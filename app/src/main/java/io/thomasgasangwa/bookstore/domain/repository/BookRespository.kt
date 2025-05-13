package io.thomasgasangwa.bookstore.domain.repository

import io.thomasgasangwa.bookstore.data.local.entity.Book

interface BookRespository {
     fun getAllBooks(): List<Book>
     fun getBookById(id: Int): Book
     fun insertBook(book: Book)
     fun updateBook(book: Book)
     fun deleteBook(book: Book)
}