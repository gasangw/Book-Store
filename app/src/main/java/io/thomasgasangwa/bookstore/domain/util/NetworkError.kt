package io.thomasgasangwa.bookstore.domain.util

sealed interface Error

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    NO_INTERNET,
    SERVER_ERROR,
    UNKNOWN
}