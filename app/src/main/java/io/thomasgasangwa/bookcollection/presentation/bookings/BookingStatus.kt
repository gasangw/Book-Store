package io.thomasgasangwa.bookcollection.presentation.bookings

enum class BookingStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}

enum class Role {
    ADMIN,
    USER
}

data class AdminBookings(
    val title: String,
    val userName: String,
    val days: String
)

val bookings = listOf(
    AdminBookings(
        title = "Sharks spears reading novels",
        userName = "Thomas",
        days = "4"
    ),
    AdminBookings(
        title = "Eagles glide through ancient libraries",
        userName = "Alice",
        days = "2"
    ),
    AdminBookings(
        title = "Lions hunt for lost manuscripts",
        userName = "James",
        days = "3"
    )
)

data class UserBookings(
    val title: String,
    val status: BookingStatus,
)
val userBookings = listOf(
    UserBookings(
        title = "Boat ride on Lake Muhazi",
        status = BookingStatus.ACCEPTED
    ),
    UserBookings(
        title = "Hiking trip in Nyungwe Forest",
        status = BookingStatus.PENDING
    ),
    UserBookings(
        title = "City tour in Kigali",
        status = BookingStatus.REJECTED
    ),
    UserBookings(
        title = "Relaxation weekend at Gisenyi",
        status = BookingStatus.ACCEPTED
    )
)