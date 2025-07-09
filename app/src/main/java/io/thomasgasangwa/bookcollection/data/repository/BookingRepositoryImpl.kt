package io.thomasgasangwa.bookcollection.data.repository

import io.thomasgasangwa.bookcollection.common.RepositoryException
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.data.local.dao.BookingDao
import io.thomasgasangwa.bookcollection.data.local.mapper.toBookingEntity
import io.thomasgasangwa.bookcollection.data.local.mapper.toBookingList
import io.thomasgasangwa.bookcollection.domain.model.Booking
import io.thomasgasangwa.bookcollection.domain.repository.BookingRepository
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class BookingRepositoryImpl(
    private val bookingDao: BookingDao
) : BookingRepository {


    override suspend fun insertBooking(booking: Booking): Result<Unit> =
        try {
            bookingDao.insertBooking(booking.toBookingEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(RepositoryException.DatabaseException("Failed to insert a booking", e))
        }


    override suspend fun updateBookingStatus(bookingId: Int, status: BookingStatus): Result<Unit> =
        try {
            bookingDao.updateBookingStatus(bookingId, status)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(RepositoryException.DatabaseException("Failed to update a booking", e))
        }

    override suspend fun updateBookingStatusByUserId(
        bookingId: Int,
        userId: String,
        status: BookingStatus
    ): Result<Unit> = try {
        bookingDao.updateBookingStatusByUserId(bookingId, userId, status)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to update a booking", e))
    }

    override fun getBookingsByUserId(userId: String): Flow<Result<List<Booking>>> =
        bookingDao.getBookingsByUserId(userId).map { it ->
            Result.Success(it.toBookingList())
        }.catch { cause ->
            Result.Failure(RepositoryException.DatabaseException("failed to fetch bookings", cause))
        }

    override suspend fun deleteBookingById(bookingId: Int): Result<Unit> = try {
        bookingDao.deleteBookingById(bookingId)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to delete a booking", e))
    }

}
