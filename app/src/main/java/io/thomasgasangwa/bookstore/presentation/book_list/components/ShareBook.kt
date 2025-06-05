package io.thomasgasangwa.bookstore.presentation.book_list.components

import android.content.Context
import android.content.Intent
import io.thomasgasangwa.bookstore.R

fun shareBook(context: Context, title: String, summary: String, coverUrl: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        //Intent.setType = "text/plain"
        putExtra(Intent.EXTRA_TEXT, summary)
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_SUBJECT, coverUrl)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    val chooserTitle = context.getString(R.string.book_shared)

    context.startActivity(
        Intent.createChooser(
            intent,
            chooserTitle
        )
    )

}