package io.thomasgasangwa.bookstore.domain.usecase

import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetAllBooksUseCase(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val localBooks = localRepository.getAllBooksStream().firstOrNull()
        if (localBooks == null) {
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

}
