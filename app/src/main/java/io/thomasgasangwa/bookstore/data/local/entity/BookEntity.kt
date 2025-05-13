package io.thomasgasangwa.bookstore.data.local.entity

@Entity(tableName = "books")
data class BookEntity(val id: Int, val title: String, val author: String, val cover: String)
