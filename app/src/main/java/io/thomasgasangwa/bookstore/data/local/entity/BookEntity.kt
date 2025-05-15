package io.thomasgasangwa.bookstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.thomasgasangwa.bookstore.common.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class BookEntity(
   @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val bookCoverUrl: String,
    val genre: String,
    val content: String?,
)
