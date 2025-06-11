package io.thomasgasangwa.bookcollection.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.thomasgasangwa.bookcollection.common.Constants
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepositoryImpl(
    private val credentialManager: CredentialManager,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(context: Context): Result<Boolean> {
        return try {
            val authCredentialResult = getGoogleAuthCredentials(context)
            if (authCredentialResult is Result.Success && authCredentialResult.value != null) {
                val credential = authCredentialResult.value
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                val isNewUser = authResult.additionalUserInfo?.isNewUser == true
                Result.Success(value = isNewUser)
                if (authResult.user != null) {
                    Result.Success(value = true)
                } else {
                    Result.Failure(IllegalArgumentException("Auth credential is null"))
                }
            } else {
                Result.Failure(Exception("Google auth credential is null"))
            }
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