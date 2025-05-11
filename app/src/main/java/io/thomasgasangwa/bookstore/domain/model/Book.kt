package io.thomasgasangwa.bookstore.domain.model

import kotlinx.serialization.SerialName

data class Book(
    val author: String,
    val cover: String,
    val epoch: String,
    val genre: String,
    val kind: String,
    val slug: String,
    val title: String,
)
