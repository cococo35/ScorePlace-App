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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocalSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel: LocalSignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //EditText 상의 입력값이 달라질 때마다 viewModel에 text값을 보냅니다.
        textChangeListener(binding.etEmail, viewModel::setEmail)
        textChangeListener(binding.etPassword, viewModel::setPassword)
        textChangeListener(binding.etUsername, viewModel::setUsername)

        //1. viewModel에서 해당 text값이 valid한지 체크한 후, true/false 값을 보내줍니다.
        //2. focusChangeListener에서는 EditText 종류 및 true/false 값에 따라 에러 메시지의 String 및 색상을 UI에 표시합니다.
        focusChangeListener(binding.etEmail, viewModel.isEmailValid, getString(R.string.email_valid), getString(R.string.email_invalid))
        focusChangeListener(binding.etPassword, viewModel.isPasswordValid, getString(R.string.password_valid), getString(R.string.password_invalid))
        focusChangeListener(binding.etUsername, viewModel.isUsernameValid, getString(R.string.username_valid), getString(R.string.username_invalid))
    }


    private fun textChangeListener(editText: EditText, updateFunction: (String) -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                updateFunction(editable.toString())
            }
        })
    }

    private fun focusChangeListener(
        editText: EditText,
        isValidFlow: StateFlow<Boolean>,
        successMessage: String,
        errorMessage: String
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {
                    isValidFlow.collect { isValid ->
                        val message = if (isValid) successMessage else errorMessage
                        val color = if (isValid) R.color.darkblue else R.color.darkmint2

                        binding.tvError.text = message
                        binding.tvError.setTextColor(ContextCompat.getColor(this@LocalSignUpActivity, color))
                    }
                }
            }
        }
    }

}