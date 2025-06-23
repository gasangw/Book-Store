package io.thomasgasangwa.bookcollection.domain.repository

import android.content.Context
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User

interface AuthRepository {
    suspend fun signIn(context: Context): Result<User?>
    suspend fun signOut(): Result<Unit>
}