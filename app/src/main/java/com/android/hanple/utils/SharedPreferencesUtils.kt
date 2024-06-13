package com.android.hanple.utils

import android.content.Context
import android.content.SharedPreferences

//context 부족해 생긴 오류 디버깅을 했다.
//참고한 글: https://youngdroidstudy.tistory.com/entry/Kotlin-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-SharedPreferences-%EB%9E%80
class SharedPreferencesUtils(context: Context) {

    companion object { //참고한 자료: https://tahaben.com.ly/2023/02/using-sharedpreferences-to-save-basic-user-info/
        private const val FIREBASE_AUTH_LOCAL = "firebase_auth"
        private const val FIREBASE_EMAIL = "email"
        private const val FIREBASE_GUEST_UID = "guest_uid"
    }

    //SharedPreferences 읽고 쓰고 지우기 - https://hapen385.tistory.com/29 코드를 참고함.
    private val spf: SharedPreferences =
        context.getSharedPreferences(FIREBASE_AUTH_LOCAL, Context.MODE_PRIVATE)


    //게스트 로그인 정보: uid
    fun saveGuestUidLocal(uid: String) {
        spf.edit()
            .putString(FIREBASE_GUEST_UID, uid)
            .apply()
    }
    fun loadGuestUidLocal(): String? {
        return spf.getString(FIREBASE_GUEST_UID, null)
    }
    fun deleteGuestUidLocal() {
        spf.edit()
            .remove(FIREBASE_EMAIL)
            .apply()
    }

    //이메일
    fun saveEmailLocal(email: String) {
        spf.edit()
            .putString(FIREBASE_EMAIL, email)
            .apply()
    }
    fun loadEmailLocal(): String? {
        return spf.getString(FIREBASE_EMAIL, null)
    }
    fun deleteEmailLocal() {
        spf.edit()
            .remove(FIREBASE_EMAIL)
            .apply()
    }
}