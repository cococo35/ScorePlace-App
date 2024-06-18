package com.android.hanple.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.hanple.databinding.ActivityLocalSignUpBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocalSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel: LocalSignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.isPasswordValid.collectLatest { isValid ->
                if (isValid) {
                    binding.tvError.text = "비밀번호 조건 만족"
                } else {
                    binding.tvError.text = "비밀번호가 8자리 미만입니다."
                }
            }
        }
    } //onCreate 끝.
} //Activity 끝.