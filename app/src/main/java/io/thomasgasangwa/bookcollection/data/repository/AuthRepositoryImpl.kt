package io.thomasgasangwa.bookcollection.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import io.thomasgasangwa.bookcollection.BuildConfig
import io.thomasgasangwa.bookcollection.common.LoginFailedException
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class AuthRepositoryImpl(
    private val credentialManager: CredentialManager,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUserIsLoggedIn: Flow<Boolean?> = callbackFlow {
        trySend(firebaseAuth.currentUser != null)
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }.conflate().distinctUntilChanged()

    override suspend fun signInAnonymously(): Result<Unit> {
        return try {
            firebaseAuth.signInAnonymously().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (authResult.user == null) {
                Result.Failure(
                    FirebaseAuthInvalidUserException(
                        "User doesn't exist",
                        "Kindly check login credentials again"
                    )
                )
            }
            val firebaseUser = authResult.user
            return firebaseUser?.let { user ->
                Timber.e("user: $user")
                val user = User(
                    email = user.email ?: "",
                    photoUrl = user.photoUrl.toString(),
                    name = user.displayName ?: ""
                )
                Result.Success(user)
            } ?: Result.Failure(Exception("Signing in your with email and password failed"))
        } catch (e: Exception) {
            Result.Failure(e)
        } catch (e: Exception) {
            Result.Failure(FirebaseAuthInvalidUserException("User doesn't exist", "$e"))
        }
    }

    override suspend fun createNewUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Unit> {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.Success(Unit)
            Timber.e("i have registered the user")
        } catch (e: Exception) {
            Result.Failure(e)
        }
        return Result.Success(Unit)
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
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
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
                    photoUrl = it.photoUrl.toString(),
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