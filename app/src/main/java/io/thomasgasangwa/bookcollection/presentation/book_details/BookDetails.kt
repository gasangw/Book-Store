package io.thomasgasangwa.bookcollection.presentation.book_details

import BookStoreTheme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.common.calculateDaysBetweenBookingDates
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.model.Booking
import io.thomasgasangwa.bookcollection.domain.model.toBookParcelableData
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.BookingUiState
import io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.BookingViewModel
import io.thomasgasangwa.bookcollection.presentation.borrow_dialog.CalendarDialog
import io.thomasgasangwa.bookcollection.presentation.borrow_dialog.ConfirmBookingDialog
import io.thomasgasangwa.bookcollection.presentation.favorites.FavoriteViewModel
import io.thomasgasangwa.bookcollection.presentation.update_book.BookParcelableData
import io.thomasgasangwa.bookcollection.presentation.view.LocalUserData
import io.thomasgasangwa.bookcollection.presentation.view.components.BookCover
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    bookId: Int?,
    userId: String,
    onEditBook: (BookParcelableData) -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    var selectedStartDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var selectedEndDate by rememberSaveable { mutableStateOf<Long?>(null) }

    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val bookingViewModel: BookingViewModel = koinViewModel(
        parameters = { parametersOf(userId) }
    )

    val bookDetailsViewModel: BookDetailsViewModel =
        koinViewModel(parameters = { parametersOf(bookId) })

    val bookDetailState by bookDetailsViewModel.state.collectAsStateWithLifecycle()

    val bookingState by bookingViewModel.state.collectAsStateWithLifecycle()

    val isBooked = bookingState.usersBookings.any { it.bookId == bookId }

    val bookingForThisBook = bookingState.usersBookings.find { it.bookId == bookId }


    val booking = Booking(
        bookingId = 0,
        bookId = bookId ?: 0,
        userId = userId ?: "",
        startDate = selectedStartDate ?: 0,
        endDate = selectedEndDate ?: 0,
        status = BookingStatus.PENDING
    )

    Timber.e("bookinng ${booking.status?.name}")

    BookDetailsContent(
        bookDetailState = bookDetailState,
        updateFavoriteStatus = { id, isFavorite ->
            favoriteViewModel.updateFavoriteStatus(id, isFavorite)
        },
        onEditBook = onEditBook,
        isBooked = isBooked,
        showDialog = showDialog,
        onShowDialogChange = { it -> showDialog = it },
        bookingState = bookingState,
        openBookingDialog = { bookingViewModel.openBookingDialog() },
        closeBookingDialog = { bookingViewModel.closeBookingDialog() },
        onStartDateChange = { selectedStartDate = it },
        onEndDateChange = { selectedEndDate = it },
        onBookButtonClicked = { bookingViewModel.insertBooking(booking) },
        bookingForThisBook = bookingForThisBook,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookDetailsContent(
    bookDetailState: BookDetailsState,
    updateFavoriteStatus: (Int, Boolean) -> Unit,
    onEditBook: (BookParcelableData) -> Unit,
    isBooked: Boolean,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    bookingState: BookingUiState,
    openBookingDialog: () -> Unit,
    closeBookingDialog: () -> Unit,
    onStartDateChange: (Long?) -> Unit,
    onEndDateChange: (Long?) -> Unit,
    onBookButtonClicked: () -> Unit,
    bookingForThisBook: Booking?,
    modifier: Modifier = Modifier,
) {

    val currentUserInfo = LocalUserData.current

    val dayOfBooking =
        calculateDaysBetweenBookingDates(bookingForThisBook?.startDate, bookingForThisBook?.endDate)

    Column(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (bookDetailState) {
            is BookDetailsState.Loading -> CircularProgressIndicator()
            is BookDetailsState.Success -> {
                val book = bookDetailState.book
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { updateFavoriteStatus(book.id, !book.isFavorite) },
                        modifier = Modifier.scale(1.5f)
                    ) {
                        Icon(
                            imageVector = if (book.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    if (currentUserInfo.user?.email?.isNotEmpty() == true) {
                        IconButton(
                            onClick = { onEditBook(book.toBookParcelableData()) },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "edit",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                }
                BookCover(
                    modifier = Modifier.height(400.dp),
                    bookCoverUrl = book.cover,
                    bookingStatus = bookingForThisBook?.status
                )
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Pages: ${book.pages},",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = "Date: ${book.releaseDate},",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = "Likes: ${book.likes}",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = book.description, style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier, textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom

                ) {
                    if (currentUserInfo.user?.email?.isEmpty() == true) {
                        if (isBooked) {
                            Text(
                                text = "This book has been borrowed and will be returned after $dayOfBooking days",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Button(
                                onClick = { onShowDialogChange(!showDialog) },
                                modifier = modifier
                            ) {
                                Text(text = "Borrow Now")
                            }
                        }
                    }
                }

                if (showDialog) {
                    CalendarDialog(
                        onDismiss = { onShowDialogChange(!showDialog) },
                        onConfirm = { startDate, endDate ->
                            onStartDateChange(startDate)
                            onEndDateChange(endDate)
                            onShowDialogChange(!showDialog)
                            openBookingDialog()
                        }
                    )
                }

                if (bookingState.bookingDialogIsOpen) {
                    ConfirmBookingDialog(
                        onDismissRequest = { closeBookingDialog() },
                        onConfirmation = {
                            onBookButtonClicked() },
                        dialogTitle = "Confirm Booking",
                        dialogText = "You are booking \"${book.title}\"",
                        icon = Icons.Default.CheckCircleOutline,
                    )
                }

            }


            is BookDetailsState.Error -> {
                val bookError = bookDetailState.exception
                Text(text = "Something went wrong $bookError")
            }
        }


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun BookDetailsPreview() {
    BookStoreTheme {
        val sampleBook = BookDetailsState.Success(
            Book(
                id = 1,
                title = "Sample Book",
                cover = "sample_cover_url",
                pages = 300,
                releaseDate = "2023-01-01",
                description = "Sample description",
                likes = 0,
                isFavorite = false
            )
        )
        val booking = Booking(
            bookingId = 1,
            bookId = 1,
            userId = "4",
            startDate = 20240938,
            endDate = 24578372346,
            status = BookingStatus.PENDING
        )
        BookDetailsContent(
            bookDetailState = sampleBook,
            updateFavoriteStatus = { bookId, isFavorite -> },
            onEditBook = {},
            isBooked = false,
            showDialog = false,
            onShowDialogChange = {},
            bookingState = BookingUiState(),
            openBookingDialog = {},
            closeBookingDialog = {},
            onStartDateChange = {},
            onEndDateChange = {},
            onBookButtonClicked = {},
            bookingForThisBook = booking,
            modifier = Modifier
        )
    }
}
