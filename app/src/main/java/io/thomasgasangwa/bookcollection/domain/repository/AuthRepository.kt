package io.thomasgasangwa.bookcollection.domain.repository

import android.content.Context
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User

interface AuthRepository {
    val hasUser: Boolean
    suspend fun signIn(context: Context): Result<User?>
    suspend fun signInAnonymously(): Result<Unit>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>

    suspend fun createNewUserWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun signOut(): Result<Unit>
}