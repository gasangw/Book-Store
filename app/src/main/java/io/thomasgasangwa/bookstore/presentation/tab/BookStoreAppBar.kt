package io.thomasgasangwa.bookstore.presentation.tab

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.thomasgasangwa.bookstore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStoreTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.appBar_title))},
        modifier = modifier,
        windowInsets = WindowInsets(0)
    )
}