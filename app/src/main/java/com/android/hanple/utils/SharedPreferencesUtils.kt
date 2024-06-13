package com.android.hanple.utils

import android.content.Context
import android.content.SharedPreferences

//context 부족해 생긴 오류 디버깅을 했다.
//참고한 글: https://youngdroidstudy.tistory.com/entry/Kotlin-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-SharedPreferences-%EB%9E%80
class SharedPreferencesUtils(context: Context) { //context 매개변수를 위해 Singleton에서 class로 다시 변경함. 대신 사용하는 Activity에서 companion object로 불러오겠습니다.
    //추후, 아키텍처에 맞추어 메소드들을 옮겨주시면 됩니다.
    //내부 함수들이 다 제자리를 찾으면 SharedPreferencesUtils는 사라져도 무방합니다.

    //SharedPreferences 읽고 쓰고 지우기 - https://hapen385.tistory.com/29 코드를 참고함.
    private val spf: SharedPreferences =
        context.getSharedPreferences("remember_me", Context.MODE_PRIVATE)

    fun saveGuestUid(uid: String) { // sharedPreference에 이메일 저장.
        val editor: SharedPreferences.Editor = spf.edit()
        editor.putString("firebase_auth_guest_uid", uid).apply()
    }
    fun loadGuestUid(): String = spf.getString("firebase_auth_guest_uid", "").toString()

    fun rememberMe(email: String) { // sharedPreference에 이메일 저장.
        val editor: SharedPreferences.Editor = spf.edit()
        editor.putString("remember_me", email).apply()
    }

    //앱 실행 전에 SharedPreferences에 저장된 이메일 정보가 있다 = 로컬에 저장됨 = 자동 로그인.
    fun loadRememberMe(): String =
        spf.getString("remember_me", "").toString() //초기값 null 아닌 "" 일 것으로 예상 중.
    //defValue를 지정하는데도. spf.getStirng 타입이 String? 이라서 좀 놀랐어요.

    fun deleteRememberMe() {
        val editor = spf.edit()
        editor.remove("remember_me").apply()
    }
}