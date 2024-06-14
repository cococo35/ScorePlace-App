package com.android.hanple.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {

    // (mutable, immutable) LiveData 선언해 주기.
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    sealed class AuthState { //로 observe에서 깔끔하게
        data class Success(val user: FirebaseUser?) : AuthState()
        data class Failure(val exception: Exception?) : AuthState()
    }
    private val auth: FirebaseAuth = Firebase.auth //firebase auth 가져오기.

    fun guestLogIn() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d("firebase auth", "게스트 로그인 성공")
        }
    }

    fun getUid(): String = auth.currentUser?.uid.toString()
    fun emailLogIn(email: String?, password: String?): Int {
        if (email == null || email == "") return 1
        else if (password == null || password == "") return 2
        else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { login ->
                    if (login.isSuccessful) {
                        _authState.value = AuthState.Success(auth.currentUser) //로그인 성공(현재 유저 정보)
                    } else {
                        _authState.value = AuthState.Failure(login.exception) //로그인 실패(예외 String)
                    }
                }
            return 0
        }
    }

}



