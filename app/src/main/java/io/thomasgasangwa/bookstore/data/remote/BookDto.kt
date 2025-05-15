package io.thomasgasangwa.bookstore.data.remote

import io.thomasgasangwa.bookstore.domain.model.Book
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val cover: String,
    val description: String,
    val index: Int,
    val number: Int,
    val originalTitle: String,
    val pages: Int,
    val releaseDate: String,
    val title: String
)

fun BookDto.toBook(
  likes: Int? = 0,
  comments: List<String?>? = null
) = Book(
    id = number,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    comments = comments
)
