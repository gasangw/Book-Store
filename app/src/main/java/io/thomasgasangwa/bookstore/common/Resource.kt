package io.thomasgasangwa.bookstore.common

sealed class  Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}

//sealed class BookUiState<out T> {
//    object Loading : BookUiState<Nothing>()
//    data class Success<T>(val data: T) : BookUiState<T>()
//    data class Error(val message: String) : BookUiState<Exception>()
//}