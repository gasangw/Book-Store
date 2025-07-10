package io.thomasgasangwa.bookcollection.presentation.borrow_dialog


import BookStoreTheme
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmBookingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    val context = LocalContext.current

    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = "remove moderator Icon",
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        },
        title = {
            Text(
                text = dialogTitle,
                color = MaterialTheme.colorScheme.primary
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
                    Toast.makeText(
                        context, "This book is booked successfully \uD83D\uDC4F",
                        Toast.LENGTH_SHORT
                    ).show()
                    onDismissRequest()
                }
            ) {
                Text(
                    "Book"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    "Dismiss",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun DeleteAccountDialogPreview() {
    BookStoreTheme {
        ConfirmBookingDialog(
            onDismissRequest = {},
            onConfirmation = {},
            dialogTitle = "Confirm Booking",
            dialogText = "You are booking \"Harry Potter and the Philosopher's Stone\"",
            icon = Icons.Default.CheckCircleOutline,
        )
    }
}