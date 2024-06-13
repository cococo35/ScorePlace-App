package com.android.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

//github 연결 테스트
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val signUpChecker: Int = viewModel.signUp(email, password) //여기서 회원가입!
            when(signUpChecker) {
                1 -> Toast.makeText(this, "이메일 란이 비어있어요.", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(this, "비밀번호 란이 비어있어요.", Toast.LENGTH_SHORT).show()
            }
            finish() //로그인 위에 사인업이 띄워져있으니 액티비티 끝내면 다시 로그인 페이지로 돌아간다.
        }
//            lateinit var localUser: User
//            localUser.email = binding.etEmail.text.toString()
//            localUser.password = binding.etPassword.text.toString()
//            localUser.name  = binding.etName.text.toString()
//            //val signUpChecker: Int = viewModel.signUp(localUser)
//            Toast.makeText(this, localUser.email, Toast.LENGTH_SHORT).show()
////            when(signUpChecker) {
////                1 -> Toast.makeText(this, "이메일 란이 비어있어요.", Toast.LENGTH_SHORT).show()
////                2 -> Toast.makeText(this, "비밀번호 란이 비어있어요.", Toast.LENGTH_SHORT).show()
////            }
//        }

        // 뒤로 가기 아이콘 클릭 리스너 설정
        binding.ivBack.setOnClickListener {
            val loginIntent = Intent(this, AuthActivity::class.java)
            startActivity(loginIntent)
            finish() // 현재 액티비티를 종료
        }

//        binding.btnSignup.setOnClickListener {
////            handleSignup(editor, sharedPreference)
//        }
//
//        watchEditText()
//
//        viewModel.signupSuccess.observe(this) { success ->
//            if (success) {
//                Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                eventFail()
//            }
//        }
//
//        viewModel.signupData.observe(this) { signupData ->
//            binding.btnSignup.isEnabled = signupData.checkStatus()
//        }
//    }
//
//    private fun handleSignup(editor: SharedPreferences.Editor, sharedPreference: SharedPreferences) {
//        // 회원가입 버튼 누를 시 id, pw 칸 정보 저장
//        val etId = binding.etId.text.toString()
//        val etPw = binding.etPassword.text.toString()
//
//        editor.putString("userId", etId)
//        editor.putString("userPw", etPw)
//        editor.apply()
//
//        // 저장된 id, pw 로그에 띄워줌
//        val savedUserId = sharedPreference.getString("userId", null)
//        val savedUserPw = sharedPreference.getString("userPw", null)
//
//        if (savedUserId != null && savedUserPw != null) {
//            Log.d("유저 정보", "저장된 ID: $savedUserId")
//            Log.d("유저 정보", "저장된 PW: $savedUserPw")
//        } else {
//            Log.d("유저 정보", "저장된 ID/PW가 없습니다.")
//        }
//
//        if (viewModel.checkStatus()) {
//            viewModel.registerUser()
//        } else {
//            eventFail()
//        }
//    }
//
//    private fun eventFail() {
//        Toast.makeText(
//            this@SignUpActivity, getString(R.string.ts_signup_error), Toast.LENGTH_SHORT
//        ).show()
//    }
//
//    private fun watchEditText() {
//        binding.apply {
//            listOf(
//                etNumber,
//                etName,
//                etId,
//                etPassword
//            ).forEach { editables ->
//                editables.addTextChangedListener { editable ->
//                    val field = when (editables.id) {
//                        etName.id -> SignUpViewModel.Field.NAME
//                        etId.id -> SignUpViewModel.Field.ID
//                        etPassword.id -> SignUpViewModel.Field.PASSWORD
//                        etNumber.id -> {
//                            if (ConvertUtils.phoneNumberRegex.containsMatchIn(editable.toString())) SignUpViewModel.Field.PHONE else null //Unix 시간 변환, Regex 변환과 같은 단순 변환 코드는 ConvertUtils에 모아 두었어요!
//                        }
//                        else -> null
//                    }
//                    field?.let {
//                        viewModel.updateSignupData(it, editable.toString())
//                    }
//                }
//            }
//        }
//    }
}}
