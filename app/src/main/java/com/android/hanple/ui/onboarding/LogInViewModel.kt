package com.android.hanple.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    //Model에서 메소드 불러오기
    private val authRepository = AuthRepository()

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // 이메일, 비밀번호 업데이트
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun logInWithEmail(email: String, password: String) {
        //TODO: SharedPref에 개인정보 보내기
        //TODO: firestore에 개인정보 보내기
        viewModelScope.launch { //ViewModel이므로 viewModelScope 안에서 논다.
            val result = authRepository.signInWithEmailAndPassword(email, password) //model에 정보 보내기
        }
    }

    fun logInGuest() {
        //TODO: SharedPref에 개인정보 보내기
        //TODO: firestore에 개인정보 보내기
        viewModelScope.launch { //ViewModel이므로 viewModelScope 안에서 논다.
            val result = authRepository.signInAnonymously() //model에서 회원가입 처리
        }
    }

}



