package io.thomasgasangwa.bookstore.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import io.thomasgasangwa.bookstore.common.Constants
import io.thomasgasangwa.bookstore.common.Result
import io.thomasgasangwa.bookstore.domain.repository.AuthRepository
import java.util.UUID

class AuthRepositoryImpl(
    private val credentialManager: CredentialManager
) : AuthRepository {

    override suspend fun signIn(context: Context): Result<Boolean> {
        return try {
            getGoogleAuthCredentials(context)
            Result.Success(value = true)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private suspend fun getGoogleAuthCredentials(context: Context): Result<AuthCredential?> {
        return try {
            val nonce = UUID.randomUUID().toString()
            val signInWithGoogle: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(
                Constants.GOOGLE_CLIENT_ID
            ).setNonce(nonce).build()

            val request: GetCredentialRequest = GetCredentialRequest
                .Builder()
                .addCredentialOption(signInWithGoogle)
                .build()

            val credential = credentialManager.getCredential(context, request).credential
            val googleTokenId = GoogleIdTokenCredential.createFrom(credential.data).idToken
            val authCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
            Result.Success(authCredential)
        } catch (e: Exception) {
            Result.Failure(e)
        }

    }
}