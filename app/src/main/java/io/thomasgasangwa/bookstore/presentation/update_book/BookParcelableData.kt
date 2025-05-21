package io.thomasgasangwa.bookstore.presentation.update_book

import android.os.Parcelable
import io.thomasgasangwa.bookstore.domain.model.Book
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookParcelableData(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val description: String,
    val pages: Int,
    val cover: String,
    val likes: Int,
    val isFavorite: Boolean
) : Parcelable


fun BookParcelableData.toBook(): Book = Book(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    isFavorite = isFavorite
)