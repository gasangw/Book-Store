package io.thomasgasangwa.bookcollection.domain.model

import io.thomasgasangwa.bookcollection.presentation.update_book.BookParcelableData


data class Book(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val description: String,
    val pages: Int,
    val cover: String,
    val likes: Int,
    val isFavorite: Boolean
)

fun Book.toBookParcelableData(): BookParcelableData = BookParcelableData(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    isFavorite = isFavorite
)