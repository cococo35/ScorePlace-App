package com.android.hanple.ui.onboarding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun createUserWithEmailAndPassword(
        email: String, password: String): Result<FirebaseUser> = try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    suspend fun signInWithEmailAndPassword(
        email: String, password: String): Result<FirebaseUser> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user!!)
    } catch (e: Exception) {
        Result.failure(e)
    }
    suspend fun signInAnonymously(): Result<FirebaseUser> = try {
        val result = auth.signInAnonymously().await()
        Result.success(result.user!!)
    } catch (e: Exception) {
        Result.failure(e)
    }
}