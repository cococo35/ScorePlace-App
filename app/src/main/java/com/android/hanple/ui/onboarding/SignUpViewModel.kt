package com.android.hanple.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    //Model에서 메소드 불러오기
    private val authRepository = AuthRepository()

    //StateFlow로 UI 상의 입력정보를 stream에 졸졸 흘려보냄
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid: StateFlow<Boolean> = _isEmailValid

    private val _username: MutableStateFlow<String> = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _isUsernameValid = MutableStateFlow(false)
    val isUsernameValid: StateFlow<Boolean> = _isUsernameValid

    //StateFlow에 값 추가하고, 실시간 입력값 확인하기
    fun setPassword(password: String) {
        _password.value = password
        _isPasswordValid.value = password.length >= 8
    }
    fun setEmail(email: String) {
        _email.value = email
        _isEmailValid.value = android.util.Patterns
            .EMAIL_ADDRESS.matcher(email).matches()
        //android.util.Patterns.EMAIL_ADDRESS로 이메일 형식 확인 가능. 안드로이드 내장값
    }
    fun setUsername(username: String) {
        _username.value = username
        _isUsernameValid.value = username.length >= 2 && username.isNotBlank() //한국어 한글자도 length = 1 로 취급
    }

    //회원가입 시 작동하는 메소드
    fun signUpWithEmail(email: String, password: String, username: String) {
        //TODO: SharedPref에 개인정보 보내기
        //TODO: firestore에 개인정보 보내기
        viewModelScope.launch { //ViewModel이므로 viewModelScope 안에서 논다.
            val result = authRepository.createUserWithEmailAndPassword(email, password)
        }
    }

}

