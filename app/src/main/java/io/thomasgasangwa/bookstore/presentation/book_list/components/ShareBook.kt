package io.thomasgasangwa.bookstore.presentation.book_list.components

import android.content.Context
import android.content.Intent
import io.thomasgasangwa.bookstore.R

fun shareBook(context: Context, subject: String, summary: String, pages: Int) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
        putExtra(Intent.EXTRA_TEXT, pages)

    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.share_book)
        )
    )

}