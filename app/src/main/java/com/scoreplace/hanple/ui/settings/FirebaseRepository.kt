package com.scoreplace.hanple.ui.settings

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun updateUserName(newUserName: String) {
        val user = auth.currentUser ?: throw Exception("유저가 로그인되지 않았습니다.")
        val userRef = db.collection("users").document(user.uid)

        // "userName" 필드만 업데이트
        userRef.update("userName", newUserName).await()
    }
}