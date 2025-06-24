package io.thomasgasangwa.bookcollection.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.thomasgasangwa.bookcollection.common.Constants.BOOKINGS

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
    val startDate: Long,
    val endDate: Long
)