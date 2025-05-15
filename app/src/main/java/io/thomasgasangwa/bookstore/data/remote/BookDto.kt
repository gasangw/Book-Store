package io.thomasgasangwa.bookstore.data.remote

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

