package io.thomasgasangwa.bookstore.domain.repository

import android.content.Context
import io.thomasgasangwa.bookstore.common.Result

interface AuthRepository {
    suspend fun signIn(context: Context): Result<Boolean>
}