package com.android.hanple.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.android.hanple.R
import com.android.hanple.databinding.ActivitySignUpBinding
import com.android.hanple.utils.ConvertUtils

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignupViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //시간 변환 테스트 시작
        Log.d("MainActivity", ConvertUtils.unixTimeConverter(1717053033).toString())
        //1717053033 SECONDS SINCE JAN 01 1970. (UTC) 4:10:37 PM
        //시간 변환 테스트 끝
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로 가기 아이콘 클릭 리스너 설정
        binding.ivBack.setOnClickListener {
            val loginIntent = Intent(this, LogInActivity::class.java)
            startActivity(loginIntent)
            finish() // 현재 액티비티를 종료
        }

//        val etId = binding.etId
//        val etPassword = binding.etPassword

// 여기서부터 feat/SharedPreferences 코드입니다.
        // DataStore 사용 전 SharedPreferences로 간단히 유저 id, pw 로컬 저장을 테스트함.
        // 참고한 자료: https://velog.io/@dlrmwl15/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-SharedPreferences

//        binding.btnSignup.setOnClickListener { // 회원가입 버튼 누를 시 id, pw 칸 정보 저장 -> ViewModel과 연겵된 클릭 리스너 코드와 충돌할 수 있어서 주석 처리함.
//            val etId = binding.etId.text.toString()
//            val etPw = binding.etPassword.text.toString()
//
//            val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
//            val editor: SharedPreferences.Editor = sharedPreference.edit()
//            editor.putString("userId", etId)
//            editor.putString("userPw", etPw)
//            editor.apply()
//        }

//        binding.btnLogin.setOnClickListener {// 저장된 id, pw 로그에 띄워줌 -> btnLogin 타 화면으로 이동해 주석 처리함.
//            val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
//            val savedUserId = sharedPreference.getString("userId", "")
//            val savedUserPw = sharedPreference.getString("userPw", "")
//            Log.d("유저 정보", "저장된 ID: " + savedUserId)
//            Log.d("유저 정보", "저장된 PW: " + savedUserPw)
//        }

// 여기까지 feat/SharedPreferences 코드입니다.

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
