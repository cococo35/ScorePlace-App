package com.android.hanple.utils

import android.content.Context

//context 부족해 생긴 오류 디버깅을 했다.
//참고한 글: https://youngdroidstudy.tistory.com/entry/Kotlin-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-SharedPreferences-%EB%9E%80
class SharedPreferencesUtils(context: Context) {
    companion object { //참고한 자료: https://tahaben.com.ly/2023/02/using-sharedpreferences-to-save-basic-user-info/
        private const val FIREBASE_AUTH_LOCAL = "firebase_auth"
        private const val FIREBASE_GUEST_UID = "guest_uid"
    }

    private val sharedPref = context.getSharedPreferences(FIREBASE_AUTH_LOCAL, Context.MODE_PRIVATE)

    fun saveGuestUid(uid: String) { // sharedPreference에 이메일 저장.
        sharedPref.edit()
            .putString(FIREBASE_GUEST_UID, uid)
            .apply()
    }
    fun loadGuestUid(): String? {
        return sharedPref.getString(FIREBASE_GUEST_UID, null)
    }
}