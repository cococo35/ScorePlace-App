package com.android.hanple.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.android.hanple.R
import com.android.hanple.databinding.ActivitySignUpBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로 가기 아이콘 클릭 리스너 설정
        binding.ivBack.setOnClickListener {
            val loginIntent = Intent(this, LogInActivity::class.java)
            startActivity(loginIntent)
            finish() // 현재 액티비티를 종료합니다.
        }

        binding.btnSignup.setOnClickListener {
            if (viewModel.checkStatus()) {
                viewModel.registerUser()
            } else {
                eventFail()
            }
        }

        watchEditText()

        viewModel.signupSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                eventFail()
            }
        })

        viewModel.signupData.observe(this, Observer { signupData ->
            binding.btnSignup.isEnabled = signupData.checkStatus()
        })
    }

    private fun eventFail() {
        Toast.makeText(
            this@SignupActivity, getString(R.string.ts_signup_error), Toast.LENGTH_SHORT
        ).show()
    }

    private fun watchEditText() {
        binding.apply {
            listOf(
                etNumber,
                etName,
                etId,
                etPassword
            ).forEach { editables ->
                editables.addTextChangedListener { editable ->
                    val field = when (editables.id) {
                        etName.id -> SignupViewModel.Field.NAME
                        etId.id -> SignupViewModel.Field.ID
                        etPassword.id -> SignupViewModel.Field.PASSWORD
                        etNumber.id -> {
                            if (phoneNumberRegex.containsMatchIn(editable.toString())) SignupViewModel.Field.EMAIL else SignupViewModel.Field.PHONE
                        }
                        else -> null
                    }
                    field?.let {
                        viewModel.updateSignupData(it, editable.toString())
                    }
                }
            }
        }
    }

    companion object {
        private val phoneNumberRegex = "^\\+?\\d{1,4}[- ]?\\d{4,}(?:[- ]?\\d{4,})?\$".toRegex()
    }
}
