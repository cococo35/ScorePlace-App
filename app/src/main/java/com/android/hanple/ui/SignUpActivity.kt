package com.android.hanple.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import com.android.hanple.DataStoreModule
import com.android.hanple.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    //dataStore 시작
    private val Context.dataStore by preferencesDataStore(
        name = DataStoreModule.PREFS_NAME
    )
    //NotePrefs class에서 생성한 private val dataStore: DataStore<Preferences> 가져오기
    private val notePrefs: DataStoreModule by lazy {
        DataStoreModule(
            applicationContext.getSharedPreferences(
                DataStoreModule.PREFS_NAME,
                Context.MODE_PRIVATE),
            dataStore
        )
    }




    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val etId = binding.etId
//        val etPassword = binding.etPassword


        // DataStore 사용 전 SharedPreferences로 간단히 유저 id, pw 로컬 저장을 테스트함.
        // 참고한 자료: https://velog.io/@dlrmwl15/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-SharedPreferences
        binding.btnSignup.setOnClickListener { // 회원가입 버튼 누를 시 id, pw 칸 정보 저장
            val etId = binding.etId.text.toString()
            val etPw = binding.etPassword.text.toString()

            val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.putString("userId", etId)
            editor.putString("userPw", etPw)
            editor.apply()
        }

        binding.btnLogin.setOnClickListener {// 저장된 id, pw 로그에 띄워줌
            val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
            val savedUserId = sharedPreference.getString("userId", "")
            val savedUserPw = sharedPreference.getString("userPw", "")
            Log.d("유저 정보", "저장된 ID: " + savedUserId)
            Log.d("유저 정보", "저장된 PW: " + savedUserPw)
        }

    }
}




