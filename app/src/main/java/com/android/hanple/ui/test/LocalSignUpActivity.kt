package com.android.hanple.ui.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(editable: Editable?) {
                viewModel.setPassword(editable.toString()) //viewModel.setPassword 메소드에 매개변수로 etPassword text 넣기
            }
        })

        // 비밀번호 유효성 상태 관찰
        lifecycleScope.launch {
            viewModel.isPasswordValid.collect { isValid ->
                binding.tvError.text = if (isValid) "비밀번호 8자 이상 통과" else ""
            }
        }
    }
}