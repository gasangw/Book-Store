package io.thomasgasangwa.bookcollection.presentation.profile

import BookStoreTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val authViewModel: SignInViewModel = koinViewModel()
    val state by authViewModel.state.collectAsStateWithLifecycle()
    val user = state.user

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.outlineVariant)
        ) {
            Image(
                painter = painterResource(R.drawable.outline_person_24),
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .matchParentSize()
                    .padding(10.dp)
                    .clip(CircleShape)
            )
        }

        if(user?.name == "Anonymous") {
            Text(
                text = "Anonymous User",
                style = MaterialTheme.typography.titleLarge,
            )
        } else {
            Text(
                text = user?.name ?: "Anonymous User",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        if (user?.email?.isNullOrEmpty() == true) {
            Text(
                text = "example@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            Text(
                text = user?.email ?: "example@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    BookStoreTheme {
        ProfileScreen()
    }
}