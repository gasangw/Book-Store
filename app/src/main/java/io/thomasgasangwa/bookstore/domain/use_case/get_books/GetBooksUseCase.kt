package io.thomasgasangwa.bookstore.domain.use_case.get_books

import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.data.remote.dto.toBook
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.repository.BookRespository

class GetBooksUseCase (
    private val repository: BookRespository
) {


    suspend operator fun invoke(): Result<List<Book>, Exception> {
        return try {
            val books: List<Book> = repository.getAllBooks().map { it.toBook() }
            Result.Success(books)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
