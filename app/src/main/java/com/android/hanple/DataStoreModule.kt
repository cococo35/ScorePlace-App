package com.android.hanple

import android.content.Context
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

//참고 자료: https://velog.io/@godmin66/Android-DataStore-%EC%A0%81%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0
class DataStoreModule(private val context: Context) { // 참고 자료 class 이름을 그대로 사용했습니다. 바꾸셔도 됩니다.
    companion object { //다른 곳에서 DataStoreModule.savedUserIdKey 이런 식으로 불러올 수 있음.
        private val Context.dataStore by preferencesDataStore(name = "dataStore")

        private val savedUserIdKey = stringPreferencesKey("savedUserId")
        private val savedUserPwKey = stringPreferencesKey("savedUserPw")
    }

    val getUserId: Flow<String> = context.dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preference ->
            preference[savedUserIdKey] ?: "" //init 값으로 추정
        }


    val getUserPw: Flow<String> = context.dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preference ->
            preference[savedUserPwKey] ?: "" //init 값으로 추정
        }

    suspend fun saveUserId(userId : String) {
        context.
    }
}

