package io.thomasgasangwa.bookcollection.presentation.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        strokeWidth = 2.dp,
        color = ProgressIndicatorDefaults.circularColor,
        modifier = modifier.padding(5.dp)
    )
}