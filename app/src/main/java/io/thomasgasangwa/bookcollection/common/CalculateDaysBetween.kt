package io.thomasgasangwa.bookcollection.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDaysBetweenBookingDates(startDateMills: Long?, endDateMills: Long?): Long {
    val startDateInstant = Instant.ofEpochMilli(startDateMills ?: 0)
    val endDateInstant = Instant.ofEpochMilli(endDateMills ?: 0)

    val bookingDays = ChronoUnit.DAYS.between(startDateInstant, endDateInstant)

    return bookingDays
}