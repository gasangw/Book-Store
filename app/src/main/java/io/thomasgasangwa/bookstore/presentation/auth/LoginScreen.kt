package io.thomasgasangwa.bookstore.presentation.auth

import BookStoreTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.auth.components.LoginButton

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo), contentDescription = "app logo"
        )

        Text(
            text = "Read. Learn. Grow",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.4f))

        LoginButton(isLoading = false)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    BookStoreTheme {
        LoginScreen()
    }
}