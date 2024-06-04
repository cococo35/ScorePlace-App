package com.android.hanple.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.repository.UserRepository

class LoginViewModel : ViewModel() {

    companion object {
        val TAG = "LogInActivity, SharedPreference"
    }
    private fun saveAuthSharedPreference(sharedPreferences: SharedPreferences, email: String, password: String) {
        val validEmail = sharedPreferences.getString("VALID_EMAIL", email)
        val validPassword = sharedPreferences.getString("VALID_PASSWORD", password)
        Log.d(TAG, "sharedPreference에 ${validEmail} String 값 저장됨")
        Log.d(TAG, "sharedPreference에 ${validPassword} String 값 저장됨")
    }

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun validateUser(userId: String, password: String) {
        _loginSuccess.value = UserRepository.validateUser(userId, password)
    }
}
