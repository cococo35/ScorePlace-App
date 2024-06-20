package com.scoreplace.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivitySignUpBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //EditText 상의 입력값이 달라질 때마다 viewModel에 text값을 보냅니다.
        textChangeListener(binding.etEmail, viewModel::updateEmail)
        textChangeListener(binding.etPassword, viewModel::updatePassword)
        textChangeListener(binding.etUsername, viewModel::updateUsername)

        //1. viewModel에서 해당 text값이 valid한지 체크한 후, true/false 값을 보내줍니다.
        //2. focusChangeListener에서는 EditText 종류 및 true/false 값에 따라 에러 메시지의 String 및 색상을 UI에 표시합니다.
        focusChangeListener(binding.etEmail, viewModel.isEmailValid, getString(R.string.email_valid), getString(
            R.string.email_invalid))
        focusChangeListener(binding.etPassword, viewModel.isPasswordValid, getString(R.string.password_valid), getString(
            R.string.password_invalid))
        focusChangeListener(binding.etUsername, viewModel.isUserNameValid, getString(R.string.username_valid), getString(
            R.string.username_invalid))

        binding.btnPrivatePolicy.setOnClickListener {//이용약관 확인
            val intent = Intent(this, PrivatePolicyActivity::class.java)
            startActivity(intent)
        }

        binding.cbPrivacyPolicy.setOnCheckedChangeListener { _, isChecked -> //ViewModel에 체크박스 체크 여부 전송
            viewModel.updateReadAll(isChecked)
        }
        viewModel.readAll.observe(this) { isChecked -> //ViewModel에서
            binding.btnSignUp.isEnabled = isChecked
        }

        //회원가입 버튼을 누르면, 각 EditText에 있는 결과값을 받아
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val username = binding.etUsername.text.toString()
            //viewModel에 있는 회원가입 시도 메소드로 넘겨줍니다.
            lifecycleScope.launch {
                viewModel.signUpWithEmail(email, password, username)
            }
        }

        binding.ivBack.setOnClickListener {
            finish() //로그인 페이지로 돌아가기
        }
    }

    private fun textChangeListener(editText: EditText, updateFunction: (String) -> Unit) { // updateFunction = viewModel::set어쩌고
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
        isValidFlow: StateFlow<Boolean>, // isValidFlow = viewModel.is어쩌고Valid
        successMessage: String,
        errorMessage: String
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                lifecycleScope.launch {//Activity이므로 Activity의 lifecycle 안에서 논다.
                    isValidFlow.collectLatest { isValid ->
                        val message = if (isValid) successMessage else errorMessage
                        val color = if (isValid) R.color.darkblue else R.color.darkmint2

                        binding.tvError.text = message
                        binding.tvError.setTextColor(ContextCompat.getColor(this@SignUpActivity, color))
                    }
                }
            }
        }
    }

}
