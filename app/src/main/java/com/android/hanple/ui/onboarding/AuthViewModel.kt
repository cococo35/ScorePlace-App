package com.android.hanple.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel:ViewModel() {

    // (mutable, immutable) LiveData 선언해 주기.
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState
    //get 사용하므로 _authState 값 바뀔 때마다 authState가 갱신됨.

    private val auth: FirebaseAuth = Firebase.auth //firebase auth 가져오기.
    //앱 실행 전체에서 1번만 선언해도 되므로, LogIn + SignUp ViewModel을 한 곳에 합치기.

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ login ->
                if(login.isSuccessful) {
                    _authState.value = AuthState.Success(auth.currentUser) //로그인 성공(현재 유저 정보)
                }
                else {
                    _authState.value = AuthState.Failure(login.exception) //로그인 실패(예외 String)
                }
            }
    }

    fun signUp(email: String, password: String) {

    }

    fun getCurrentUser() = auth.currentUser

    sealed class AuthState {
        data class Success(val user: FirebaseUser?): AuthState()
        data class Failure(val exception: Exception?): AuthState()

    }


}