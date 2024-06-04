package com.android.hanple.ui.onboarding

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.repository.UserSampleData

class LogInViewModel : ViewModel() {
    companion object {
        val TAG = "LogInActivity, SharedPreference"
    }
    fun saveAuthSharedPreference(sharedPreferences: SharedPreferences, email: String, password: String) {
        val validEmail = sharedPreferences.getString("VALID_EMAIL", email)
        val validPassword = sharedPreferences.getString("VALID_PASSWORD", password)
        Log.d(TAG, "sharedPreference에 ${validEmail} String 값 저장됨")
        Log.d(TAG, "sharedPreference에 ${validPassword} String 값 저장됨")
    } // sharedPreference에 이메일 및 비밀번호 저장.

    //앱 실행 전에 SharedPreferences에 저장된 이메일, 비밀번호 정보가 있다면?
    //로컬 자동 저장 = 자동 로그인.

    private fun isRememberMeValid(): Boolean = true


    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun validateUser(userId: String, password: String) {
        _loginSuccess.value = UserSampleData.validateUser(userId, password)
    }
}
