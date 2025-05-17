//package io.thomasgasangwa.bookstore.domain.usecase
//
//import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
//import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository
//import timber.log.Timber
//
//class FetchBooksUseCase(
//    private val localRepository: LocalRepository,
//    private val remoteRepository: RemoteRepository
//) {
//    suspend operator fun invoke() {
//        val localBookCount = localRepository.getTotalBooks()
//        Timber.d("Local book count: $localBookCount")
//        if (localBookCount == 0) {
//            try {
//                Timber.d("Fetching books from remote repository")
//                val remoteBooks = remoteRepository.getRemoteBooks()
//                Timber.d("Remote book count: $remoteBooks")
//                return remoteBooks.forEach {
//                    localRepository.insertBook(it)
//                }
//            } catch (e: Exception) {
//                throw Exception("Error fetching books from remote repository ${e.message}")
//                Timber.e("error message is ${e.message}")
//            }
//        }
//        Timber.d("Local book count: $localBookCount")
//    }
//
//}
