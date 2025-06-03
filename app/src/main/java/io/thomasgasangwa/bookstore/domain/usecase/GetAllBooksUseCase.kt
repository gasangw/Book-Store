package io.thomasgasangwa.bookstore.domain.usecase

import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import io.thomasgasangwa.bookstore.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetAllBooksUseCase(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val localBooks = localRepository.getAllBooksStream().first()

        when (localBooks) {
            is Result.Success -> {
                val books = localBooks.value

                if (books.isEmpty()) {
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

            is Result.Failure -> {
                throw Exception("Error fetching books from local repository ${localBooks.exception}")
            }
        }
    }

}
