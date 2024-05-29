package com.android.hanple.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.repository.UserRepository

class LoginViewModel : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun validateUser(userId: String, password: String) {
        _loginSuccess.value = UserRepository.validateUser(userId, password)
    }
}
