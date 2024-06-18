package com.android.hanple.ui.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocalSignUpViewModel : ViewModel() {

    //StateFlow
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid

    fun setPassword(newPassword: String) {
        _password.value = newPassword
        _isPasswordValid.value = isValidPassword(newPassword)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}