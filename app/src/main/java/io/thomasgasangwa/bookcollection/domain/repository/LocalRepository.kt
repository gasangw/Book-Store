package io.thomasgasangwa.bookcollection.domain.repository

import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllBooksStream(): Flow<Result<List<Book>>>

    fun getBooksWithBookingsStream(): Flow<Result<List<Book>>>
    fun getBookStream(id: Int): Flow<Result<Book>>
    fun getFavoriteBooksStream(): Flow<Result<List<Book>>>
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Result<Unit>
    suspend fun insertBook(book: Book): Result<Unit>
    suspend fun updateBook(book: Book): Result<Unit>
    suspend fun deleteBookById(id: Int): Result<Unit>
    suspend fun updateLikes(id: Int, likes: Int): Result<Unit>


    /*
    BELOW 👇👇👇👇👇
    Here you'll find all the available methods for handling bookings.
 */

//    fun getAllBookingsStream(): Flow<Result<List<Booking>>>
//    fun getBookingsByUserIdStream(userId: String): Flow<Result<List<Booking>>>
//    suspend fun insertBooking(booking: Booking): Result<Unit>
//    suspend fun deleteBookingById(id: Int): Result<Unit>
}
