package com.android.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.android.hanple.R
import com.android.hanple.databinding.ActivityLoginBinding
import com.android.hanple.ui.LoginViewModel
import com.android.hanple.ui.MainActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding // 뷰 바인딩 객체를 늦은 초기화로 선언
    private val loginViewModel: LoginViewModel by viewModels() // ViewModel 객체를 by viewModels()로 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("AUTH_LOCAL_SAVE", MODE_PRIVATE)

        // 로그인 버튼 클릭 리스너 설정
        binding.btnLogin.setOnClickListener {
            // 입력된 사용자 아이디와 비밀번호를 가져옴
            val userId = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            // ViewModel을 통해 사용자 유효성 검사 수행
            loginViewModel.validateUser(userId, password)
        }

        // ViewModel의 로그인 결과를 관찰하여 처리
        loginViewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                // 로그인 성공 시, 메인 액티비티로 이동
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userID", binding.etId.text.toString())
                startActivity(intent)
            } else {
                // 로그인 실패 시, 오류 메시지 표시
                Toast.makeText(this, R.string.ts_login_id, Toast.LENGTH_SHORT).show()
            }
        })

        // 회원가입 텍스트뷰 클릭 리스너 설정
        binding.tvSignup.setOnClickListener {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }
}
