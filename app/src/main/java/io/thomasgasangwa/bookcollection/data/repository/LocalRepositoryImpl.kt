package io.thomasgasangwa.bookcollection.data.repository

import io.thomasgasangwa.bookcollection.common.RepositoryException
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.data.local.dao.BookDao
import io.thomasgasangwa.bookcollection.data.local.mapper.toBook
import io.thomasgasangwa.bookcollection.data.local.mapper.toBookEntity
import io.thomasgasangwa.bookcollection.data.local.mapper.toBookList
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LocalRepositoryImpl(
    private val itemDao: BookDao
) : LocalRepository {

    override fun getAllBooksStream(): Flow<Result<List<Book>>> =
        itemDao.getAllBooks().map { it ->
            Result.Success(it.toBookList())
        }.catch { cause ->
            Result.Failure(RepositoryException.DatabaseException("failed to fetch books", cause))
        }

    override fun getBooksWithBookingsStream(): Flow<Result<List<Book>>> =
        itemDao.getBooksWithBookings().map { it ->
            Result.Success(it.toBookList())
        }.catch { cause ->
            Result.Failure(RepositoryException.DatabaseException("failed to fetch books", cause))
        }

    override fun getBookStream(id: Int): Flow<Result<Book>> =
        itemDao.getBookById(id).map { it ->
            Result.Success(it.toBook())
        }.catch { cause ->
            Result.Failure(RepositoryException.NotFoundException("Book $id not found"))
        }

    override fun getFavoriteBooksStream(): Flow<Result<List<Book>>> =
        itemDao.getFavoriteBooks().map { it ->
            Result.Success(it.toBookList())
        }.catch { cause ->
            Result.Failure(
                RepositoryException.DatabaseException(
                    "Error occurred while getting favorite books",
                    cause
                )
            )
        }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean): Result<Unit> =
        try {
            itemDao.updateFavoriteStatus(id, isFavorite)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(RepositoryException.DatabaseException("Failed to update favorite", e))
        }

    override suspend fun insertBook(book: Book): Result<Unit> = try {
        itemDao.insert(book.toBookEntity())
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to insert a book", e))
    }

    override suspend fun deleteBookById(id: Int): Result<Unit> = try {
        itemDao.deleteBookById(id)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to delete a book", e))
    }

    override suspend fun updateBook(book: Book): Result<Unit> = try {
        itemDao.update(book.toBookEntity())
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to update a book", e))
    }

    override suspend fun updateLikes(id: Int, likes: Int): Result<Unit> = try {
        itemDao.updateLikes(id, likes)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Failure(RepositoryException.DatabaseException("Failed to update likes of a book", e))
    }
}


