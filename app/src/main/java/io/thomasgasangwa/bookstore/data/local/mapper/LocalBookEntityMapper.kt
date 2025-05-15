package io.thomasgasangwa.bookstore.data.local.mapper

import io.thomasgasangwa.bookstore.data.local.entity.BookEntity
import io.thomasgasangwa.bookstore.domain.model.Book

fun BookEntity.toBook() = Book(
        id = id,
        title = title,
        releaseDate = releaseDate,
        description = description,
        pages = pages,
        cover = cover,
        likes = likes,
        comments = comments
    )

fun List<BookEntity>.toBookList() = map { it.toBook() }

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    comments = comments
)