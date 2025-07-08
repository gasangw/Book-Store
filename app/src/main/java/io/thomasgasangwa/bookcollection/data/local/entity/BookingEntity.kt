package io.thomasgasangwa.bookcollection.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.thomasgasangwa.bookcollection.common.Constants.BOOKINGS
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus

@Entity(
    tableName = BOOKINGS,
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val bookingId: Int = 0,
    val bookId: Int,
    val userId: String,
    val startDate: Long,
    val endDate: Long,
    val status: BookingStatus? = null
)