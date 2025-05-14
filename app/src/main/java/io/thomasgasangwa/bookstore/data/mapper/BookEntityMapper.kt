package io.thomasgasangwa.bookstore.data.mapper

import io.thomasgasangwa.bookstore.data.local.entity.BookEntity
import io.thomasgasangwa.bookstore.domain.model.Book

fun BookEntity.toBook(
    content: String? = null
): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        bookCoverUrl = bookCoverUrl,
        genre = genre,
        content = content
    )
}

fun List<BookEntity>.toBookList() = map { it.toBook() }

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    author = author,
    bookCoverUrl = bookCoverUrl,
    genre = genre,
    content = content
)