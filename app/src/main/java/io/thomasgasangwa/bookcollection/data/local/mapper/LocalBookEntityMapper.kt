package io.thomasgasangwa.bookcollection.data.local.mapper

import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import io.thomasgasangwa.bookcollection.domain.model.Book

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    isFavorite = isFavorite
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
    isFavorite = isFavorite
)
