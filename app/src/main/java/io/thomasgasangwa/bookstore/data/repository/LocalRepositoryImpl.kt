package io.thomasgasangwa.bookstore.data.repository

import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.mapper.toBook
import io.thomasgasangwa.bookstore.data.local.mapper.toBookEntity
import io.thomasgasangwa.bookstore.data.local.mapper.toBookList
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

class LocalRepositoryImpl(
    private val itemDao: BookDao
) : LocalRepository {
    override fun getAllBooksStream(): Flow<List<Book>> =
        itemDao.getAllBooks().map { it.toBookList() }.catch { cause ->
            Timber.e("Error thrown: $cause")
        }

    override fun getBookStream(id: Int): Flow<Book> =
        itemDao.getBookById(id).map { it.toBook() }.catch { cause ->
            Timber.e("error thrown $cause")
        }

    override fun getFavoriteBooksStream(): Flow<List<Book>> =
        itemDao.getFavoriteBooks().map { it.toBookList() }.catch { cause ->
            Timber.e("error thrown $cause")
        }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) =
        try {
            itemDao.updateFavoriteStatus(id, isFavorite)
        } catch (e: Exception) {
            Timber.e("error thrown $e")
        }

    override suspend fun insertBook(book: Book) = try {
        itemDao.insert(book.toBookEntity())
    } catch (e: Exception) {
        Timber.e("error thrown $e")
    }

    override suspend fun deleteBookById(id: Int) = try {
        itemDao.deleteBookById(id)
    } catch (e: Exception) {
        Timber.e("error thrown $e")
    }

    override suspend fun updateBook(book: Book) = try {
        itemDao.update(book.toBookEntity())
    } catch (e: Exception) {
        Timber.e("error thrown $e")
    }

    override suspend fun updateLikes(id: Int, likes: Int) = try {
        itemDao.updateLikes(id, likes)
    } catch (e: Exception) {
        Timber.e("error thrown $e")
    }
}


