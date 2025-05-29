package io.thomasgasangwa.bookstore.presentation.view

import BookStoreTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.thomasgasangwa.bookstore.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Splash(modifier = modifier)
}

@Composable
fun Splash(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.book_library_cover),
            contentDescription = "splash cover",
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .matchParentSize()
        )
        Column(
            modifier = modifier
                .height(670.dp)
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.novel_house),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.background,
                modifier = modifier
            )
            Text(
                text = stringResource(R.string.read_learn_discover),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.background,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    BookStoreTheme {
        SplashScreen()
    }
}