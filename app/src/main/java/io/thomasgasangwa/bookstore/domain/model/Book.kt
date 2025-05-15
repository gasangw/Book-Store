package io.thomasgasangwa.bookstore.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val bookCoverUrl: String,
    val genre: String,
    val content: String?,
)
