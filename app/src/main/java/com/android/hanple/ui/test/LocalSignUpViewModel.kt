package com.android.hanple.ui.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LocalSignUpViewModel : ViewModel() {

    //StateFlow
    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = _password

    fun setPassword(password: String) {
        _password.value = password
    }

}