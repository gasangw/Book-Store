package io.thomasgasangwa.bookstore.common

sealed interface Result<out D, out E: Exception> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: Exception>(val error: E):
        Result<Nothing, E>
}
