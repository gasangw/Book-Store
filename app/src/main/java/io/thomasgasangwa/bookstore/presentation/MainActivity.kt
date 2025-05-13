package io.thomasgasangwa.bookstore.presentation

import BookStoreTheme
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.thomasgasangwa.bookstore.presentation.view.book_list.BookListScreen

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    BookStoreTheme {
        BookListScreen()
    }
}
