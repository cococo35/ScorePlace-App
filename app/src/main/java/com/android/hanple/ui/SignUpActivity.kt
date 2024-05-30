package com.android.hanple.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.android.hanple.R
import com.android.hanple.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
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
            finish() // 현재 액티비티를 종료
        }

        // SharedPreferences 초기화
        val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        binding.btnSignup.setOnClickListener { // 회원가입 버튼 누를 시 id, pw 칸 정보 저장
            val etId = binding.etId.text.toString()
            val etPw = binding.etPassword.text.toString()

            editor.putString("userId", etId)
            editor.putString("userPw", etPw)
            editor.apply()

            if (viewModel.checkStatus()) {
                viewModel.registerUser()
            } else {
                eventFail()
            }
        }

        binding.btnSignup.setOnClickListener { // 저장된 id, pw 로그에 띄워줌
            val savedUserId = sharedPreference.getString("userId", null)
            val savedUserPw = sharedPreference.getString("userPw", null)

            if (savedUserId != null && savedUserPw != null) {
                Log.d("유저 정보", "저장된 ID: $savedUserId")
                Log.d("유저 정보", "저장된 PW: $savedUserPw")
            } else {
                Log.d("유저 정보", "저장된 ID/PW가 없습니다.")
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
            this@SignUpActivity, getString(R.string.ts_signup_error), Toast.LENGTH_SHORT
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