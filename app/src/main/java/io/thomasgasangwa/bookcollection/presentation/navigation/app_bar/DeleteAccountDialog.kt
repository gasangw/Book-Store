package io.thomasgasangwa.bookcollection.presentation.navigation.app_bar

import BookStoreTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveModerator
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteAccountDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = "remove moderator Icon",
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = dialogTitle,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                text = dialogText,
                textAlign = TextAlign.Center,
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    "Delete",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun DeleteAccountDialogPreview() {
    BookStoreTheme {
        DeleteAccountDialog(
            onDismissRequest = {},
            onConfirmation = {},
            dialogTitle = "Delete Account",
            dialogText = "Are you sure you want to delete your account?",
            icon = Icons.Default.RemoveModerator,
        )
    }
}