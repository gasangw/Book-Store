package io.thomasgasangwa.bookcollection.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.thomasgasangwa.bookcollection.common.Constants
import io.thomasgasangwa.bookcollection.common.LoginFailedException
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val credentialManager: CredentialManager,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val hasUser: Boolean
        get() = firebaseAuth.currentUser != null

    override suspend fun signInAnonymously(): Result<Unit> {
        return try {
            firebaseAuth.signInAnonymously().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        val firebaseUser = firebaseAuth.currentUser
        return firebaseUser?.let { user ->
            val currentUser = User(
                email = user.email ?: "",
                photoUrl = user.photoUrl.toString(),
                name = user.displayName ?: "Anonymous"
            )
            Result.Success(currentUser)
        } ?: Result.Success(null)

    }


    override suspend fun signIn(context: Context): Result<User?> {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(Constants.GOOGLE_CLIENT_ID)
            .setFilterByAuthorizedAccounts(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credential = credentialManager.getCredential(
            context = context,
            request = request
        ).credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(
                googleIdTokenCredential.idToken, null
            )

            val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()

            val firebaseUser = authResult.user


            return firebaseUser?.let {
                val user = User(
                    email = it.email ?: "",
                    photoUrl = it.photoUrl.toString() ?: "",
                    name = it.displayName ?: ""
                )
                Result.Success(user)
            } ?: Result.Success(null)
        } else {
            return Result.Failure(
                LoginFailedException(
                    "Error occurred while logging in", null
                )
            )
        }
    }

    override suspend fun signOut(): Result<Unit> {
        firebaseAuth.signOut()
        return try {
            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}