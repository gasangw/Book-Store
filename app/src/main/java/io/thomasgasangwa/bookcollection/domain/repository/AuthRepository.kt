package io.thomasgasangwa.bookcollection.domain.repository

import android.content.Context
import io.thomasgasangwa.bookcollection.common.Result

interface AuthRepository {
    suspend fun signIn(context: Context): Result<Boolean>
}