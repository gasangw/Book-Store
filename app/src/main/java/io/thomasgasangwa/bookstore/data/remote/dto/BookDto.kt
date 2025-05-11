package io.thomasgasangwa.bookstore.data.remote.dto

import io.thomasgasangwa.bookstore.domain.model.Book
import kotlinx.serialization.SerialName

data class BookDto(
    val author: String,
    val cover: String,
    @SerialName("cover_color") val coverColor: String,
    @SerialName("cover_thumb")  val coverThumb: String,
    val epoch: String,
    @SerialName("full_sort_key") val fullSortKey: String,
    val genre: String,
    @SerialName("has_audio") val hasAudio: Boolean,
    val href: String,
    val kind: String,
    val liked: Any,
    @SerialName("simple_thumb") val simpleThumb: String,
    val slug: String,
    val title: String,
    val url: String
)

fun BookDto.toBook(): Book {
    return Book(
        author = author,
        cover = cover,
        epoch = epoch,
        genre = genre,
        kind = kind,
        slug = slug,
        title = title
    )
}