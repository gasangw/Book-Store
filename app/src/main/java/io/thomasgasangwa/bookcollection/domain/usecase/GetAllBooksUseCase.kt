package io.thomasgasangwa.bookcollection.domain.usecase

import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
import io.thomasgasangwa.bookcollection.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllBooksUseCase(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        try {
            val remoteBooks = remoteRepository.getRemoteBooks()
            return@withContext remoteBooks.forEach {
                localRepository.insertBook(it)
            }
        } catch (e: Exception) {
            throw Exception("Error fetching books from remote repository ${e.message}")
        }
    }
}
