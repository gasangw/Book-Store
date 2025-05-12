package io.thomasgasangwa.bookstore.domain.use_case.get_books

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import io.thomasgasangwa.bookstore.common.Resource
import io.thomasgasangwa.bookstore.data.remote.dto.toBook
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.BookRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRespository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(): Flow<Resource<List<Book>, Error>> = flow {
        try {
         emit(Resource.Loading())
            val books: List<Book> = repository.getAllBooks().map { it.toBook() }
            emit(Resource.Success(books))
        } catch (e: HttpException) {
            emit(Resource.Error(Error(e.localizedMessage ?: "An unexpected error occurred")))
        } catch (e: IOException) {
            emit(Resource.Error(Error(e.message)))
        }
    }
}