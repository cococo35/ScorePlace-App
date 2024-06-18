package com.android.hanple.ui.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocalSignUpViewModel: ViewModel() {

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid: StateFlow<Boolean> = _isEmailValid

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

}