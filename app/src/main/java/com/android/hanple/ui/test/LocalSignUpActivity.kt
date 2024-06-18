package com.android.hanple.ui.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.hanple.R
import com.android.hanple.databinding.ActivityLocalSignUpBinding
import kotlinx.coroutines.launch

class LocalSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel: LocalSignUpViewModel by viewModels()

    private fun setTextChangeListener(editText: EditText, updateFunction: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                updateFunction(editable.toString())
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextChangeListener(binding.etEmail, viewModel::setEmail)
        setTextChangeListener(binding.etPassword, viewModel::setPassword)
        setTextChangeListener(binding.etUsername, viewModel::setUsername)

        // EditText 포커스 변경 리스너 설정
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    viewModel.isPasswordValid.collect { isValid ->
                        val message = if (isValid) "비밀번호 8자 이상 통과 >.<" else "비밀번호가 8자 미만이에요;;;"
                        val color = if (isValid) R.color.darkblue else R.color.darkmint2

                        binding.tvError.text = message
                        binding.tvError.setTextColor(ContextCompat.getColor(this@LocalSignUpActivity, color))
                    }
                }
            }
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    viewModel.isEmailValid.collect { isValid ->
                        val message = if (isValid) "이메일 주소 형식이 맞네요!" else "잘못된 이메일 형식이에요 ㅠ.ㅠ"
                        val color = if (isValid) R.color.darkblue else R.color.darkmint2

                        binding.tvError.text = message
                        binding.tvError.setTextColor(ContextCompat.getColor(this@LocalSignUpActivity, color))
                    }
                }
            }
        }

        binding.etUsername.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    viewModel.isUsernameValid.collect { isValid ->
                        val message = if (isValid) "멋진 별명이네요!" else "2글자 이상의 별명을 입력해 주세요."
                        val color = if (isValid) R.color.darkblue else R.color.darkmint2

                        binding.tvError.text = message
                        binding.tvError.setTextColor(ContextCompat.getColor(this@LocalSignUpActivity, color))
                    }
                }
            }
        }
    }
}