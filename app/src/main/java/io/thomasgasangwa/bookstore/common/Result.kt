package io.thomasgasangwa.bookstore.common

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}

sealed class RepositoryException(message: String, cause: Throwable? = null) :
    Exception(message, cause) {
    class DatabaseException(message: String, cause: Throwable? = null) :
        RepositoryException(message, cause)

    class NotFoundException(message: String) : RepositoryException(message)
}