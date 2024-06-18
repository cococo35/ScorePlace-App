package com.android.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.hanple.R
import com.android.hanple.databinding.ActivityLogInBinding
import com.android.hanple.ui.search.MainActivity
import com.android.hanple.utils.GenerateNicknameUtils
import com.android.hanple.utils.SharedPreferencesUtils

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val authViewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Hanple)
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmailLogin.setOnClickListener {//앱 자체 null 체크
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val logInChecker: Int = authViewModel.emailLogIn(email, password) //여기서 로그인!
            when(logInChecker) {
                1 -> Toast.makeText(this, getText(R.string.onboarding_email_empty), Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, getText(R.string.onboarding_password_empty), Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnGuestLogin.setOnClickListener{
            authViewModel.guestLogIn() //firebase에 게스트 로그인 계정 생성

            val spf = SharedPreferencesUtils(applicationContext)
            val savedNickname: String? = spf.loadNicknameLocal()
            lateinit var nickname: String

            if (savedNickname == null) { //저장된 별명이 없을 때 새로 생성해 저장.
                nickname = GenerateNicknameUtils.generateNickname()
                spf.saveNicknameLocal(nickname)
                Log.d("nickname", "별명 생성: $nickname")
            } else { //저장된 별명이 있을 때
                nickname = savedNickname
                Log.d("nickname", "별명이 이미 $nickname 으로 작성되어 있음.")
            }
            Toast.makeText(this, "반가워요, $nickname 님!", Toast.LENGTH_SHORT).show()
            val mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.putExtra("nickname", nickname)
            Log.d("nickname", "닉네임: $nickname | 인텐트에 포장해 메인으로 보냄.")
            startActivity(mainIntent)
            finish()
        }

        // 회원가입 텍스트뷰 클릭 리스너 설정
        binding.tvSignup.setOnClickListener {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }

        authViewModel.authState.observe(this, Observer { authState -> //firebase auth 계정 체크
            when (authState) {
                is LogInViewModel.AuthState.Success -> {
                    Toast.makeText(this, getText(R.string.onboarding_firebase_auth_success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // 현재 Activity를 종료하여 뒤로가기 시 로그인 화면이 보이지 않도록 함
                }
                is LogInViewModel.AuthState.Failure -> {
                    val exception: String = "${authState.exception?.message}"
                    var toastMessage: String = ""
                    Log.d("Firebase Auth Failed", exception)
                    when (exception) {
                        "The supplied auth credential is incorrect, malformed or has expired." -> toastMessage = getString(R.string.onboarding_check_email_password)
                        "The email address is badly formatted." -> toastMessage = getString(R.string.onboarding_email_format_wrong)
                    }
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}