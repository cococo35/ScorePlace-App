package com.android.hanple.utils

import android.content.SharedPreferences

object SharedPreferencesUtils { //우선은 Singleton으로 선언해 어디에서든 사용가능하게 두었습니다.
    //추후, 아키텍처에 맞추어 메소드들을 옮겨주시면 됩니다.
    //내부 함수들이 다 제자리를 찾으면 SharedPreferencesUtils는 사라져도 무방합니다.

    //SharedPreferences 읽고 쓰고 지우기 - https://hapen385.tistory.com/29 코드를 참고함.
    fun rememberMe(spf: SharedPreferences, email: String) { // sharedPreference에 이메일 저장.
        val editor: SharedPreferences.Editor = spf.edit()
        editor.putString("remember_me", email).apply()
    }
    //앱 실행 전에 SharedPreferences에 저장된 이메일 정보가 있다 = 로컬에 저장됨 = 자동 로그인.
    fun loadRememberMe(spf: SharedPreferences) =
        spf.getString("remember_me", "") //초기값 null 아닌 ""

    fun deleteRememberMe(spf: SharedPreferences) {
        val editor = spf.edit()
        editor.remove("remember_me").apply()
    }
}