package com.android.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.hanple.databinding.ActivityOnboardingBinding
import com.android.hanple.ui.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            authViewModel.logIn(email, password)
            //email이나 password가 하나라도 null이면 현재 앱이 강제 종료됨.
        }

        // 회원가입 텍스트뷰 클릭 리스너 설정
        binding.tvSignup.setOnClickListener {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }

        authViewModel.authState.observe(this, Observer { authState ->
            when (authState) {
                is AuthViewModel.AuthState.Success -> {
                    Toast.makeText(this, "Firebase Auth 인증 성공!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // 현재 Activity를 종료하여 뒤로가기 시 로그인 화면이 보이지 않도록 함
                }
                is AuthViewModel.AuthState.Failure -> {
                    Toast.makeText(this, "Firebase Auth 인증 실패: ${authState.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}