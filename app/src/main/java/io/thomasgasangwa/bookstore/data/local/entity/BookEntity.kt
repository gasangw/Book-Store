package io.thomasgasangwa.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
   @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val cover: String?,
    val genre: String,
    val description: String,
)
