package com.android.hanple.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.hanple.databinding.ActivityLocalSignUpBinding
import kotlinx.coroutines.launch

class LocalSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel: LocalSignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText 포커스 변경 리스너 설정
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    viewModel.isPasswordValid.collect { isValid ->
                        binding.tvError.text =
                            if (isValid) "비밀번호 8자 이상 통과 >.<" else "비밀번호가 8자 미만이에요;;;"
                    }
                }
            }
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    viewModel.isEmailValid.collect { isValid ->
                        binding.tvError.text =
                            if (isValid) "이메일 주소 형식이 맞네요!" else "잘못된 이메일 형식이에요 ㅠ.ㅠ"
                    }
                }
            }
        }1
    }
}