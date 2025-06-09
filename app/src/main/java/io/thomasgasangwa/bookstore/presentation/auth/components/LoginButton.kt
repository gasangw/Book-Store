package io.thomasgasangwa.bookstore.presentation.auth.components

import BookStoreTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.R

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    Button(onClick = {}) {
        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = ProgressIndicatorDefaults.circularColor
            )
        } else {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "google icon",
                    modifier = modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Login with Google")
            }
        }
    }
}

@Preview
@Composable
private fun LoginButtonPreview() {
    BookStoreTheme {
        LoginButton(isLoading =  false)
    }
}