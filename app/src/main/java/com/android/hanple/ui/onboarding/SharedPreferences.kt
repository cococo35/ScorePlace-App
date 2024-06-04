package com.android.hanple.ui.onboarding

import android.content.SharedPreferences

class SharedPreferences(spf: SharedPreferences) {
    companion object {
        val TAG = "LogInActivity, SharedPreference"
    }

    //SharedPreferences 읽고 쓰고 지우기 - https://hapen385.tistory.com/29 코드를 참고함.
    fun rememberMe(spf: SharedPreferences, email: String) { // sharedPreference에 이메일 저장.
        val editor: SharedPreferences.Editor = spf.edit()
        editor.putString("remember_me", email).apply()
    }
    //앱 실행 전에 SharedPreferences에 저장된 이메일 정보가 있다 = 로컬에 저장됨 = 자동 로그인.
    private fun loadRememberMe(spf: SharedPreferences) =
        spf.getString("remember_me", "") //초기값 null 아닌 ""

    private fun deleteRememberMe(spf: SharedPreferences) {
        val editor = spf.edit()
        editor.remove("remember_me").apply()
    }
}