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
import com.android.hanple.ui.MainActivity
import com.android.hanple.utils.SharedPreferencesUtils

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val logInViewModel: LogInViewModel by viewModels()

    private fun initView(){
        if (binding.cbAutoLogin.isChecked) {
            Toast.makeText(this, "자동 로그인 되었습니다.\n환영해요, (닉네임) 님!", Toast.LENGTH_SHORT).show()
        }
        else if ( binding.cbAutoLogin.isChecked) {
            binding.etEmail.setText(SharedPreferencesUtils(applicationContext).loadRememberMe())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Hanple)
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initView()

        binding.btnLogin.setOnClickListener {//앱 자체 null 체크
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val logInChecker: Int = logInViewModel.logIn(email, password) //여기서 로그인!
            when(logInChecker) {
                1 -> Toast.makeText(this, "이메일 란이 비어있어요.", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, "비밀번호 란이 비어있어요.", Toast.LENGTH_SHORT).show()

                0 -> SharedPreferencesUtils(applicationContext).rememberMe(email) //로그인 시 이메일 자동 저장
            }
        }

        // 회원가입 텍스트뷰 클릭 리스너 설정
        binding.tvSignup.setOnClickListener {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }

        logInViewModel.authState.observe(this, Observer { authState -> //firebase auth 계정 체크
            when (authState) {
                is LogInViewModel.AuthState.Success -> {
                    Toast.makeText(this, "Firebase Auth 인증 성공!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // 현재 Activity를 종료하여 뒤로가기 시 로그인 화면이 보이지 않도록 함
                }
                is LogInViewModel.AuthState.Failure -> {
                    val exception: String = "${authState.exception?.message}"
                    var toastMessage: String = ""
                    Log.d("Firebase Auth Failed", exception)
                    when (exception) {
                        "The supplied auth credential is incorrect, malformed or has expired." -> toastMessage = "아이디나 비밀번호를 확인해 주세요."
                        "The email address is badly formatted." -> toastMessage = "올바른 이메일 형식이 아닙니다."
                    }
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}