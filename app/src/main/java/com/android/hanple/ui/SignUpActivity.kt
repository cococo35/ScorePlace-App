package com.android.hanple.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.hanple.R
import com.android.hanple.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {


    //firebase auth 테스트. https://firebase.google.com/docs/auth/android/start#kotlin+ktx
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    //@RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 시간 변환 테스트 시작
        //Log.d("MainActivity", ConvertUtils.unixTimeConverter(1717053033).toString())

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

        binding.btnSignup.setOnClickListener {
            handleSignup(editor, sharedPreference)
        }

        watchEditText()

        viewModel.signupSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                eventFail()
            }
        }

        viewModel.signupData.observe(this) { signupData ->
            binding.btnSignup.isEnabled = signupData.checkStatus()
        }

        //firebase auth
        auth = Firebase.auth //onCreate에서 초기화
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser //
//        if (currentUser != null) {
//            reload()
//        }
//    }

    private fun handleSignup(editor: SharedPreferences.Editor, sharedPreference: SharedPreferences) {
        // 회원가입 버튼 누를 시 id, pw 칸 정보 저장
        val etId = binding.etId.text.toString()
        val etPw = binding.etPassword.text.toString()

        editor.putString("userId", etId)
        editor.putString("userPw", etPw)
        editor.apply()

        // 저장된 id, pw 로그에 띄워줌
        val savedUserId = sharedPreference.getString("userId", null)
        val savedUserPw = sharedPreference.getString("userPw", null)

        if (savedUserId != null && savedUserPw != null) {
            Log.d("유저 정보", "저장된 ID: $savedUserId")
            Log.d("유저 정보", "저장된 PW: $savedUserPw")
        } else {
            Log.d("유저 정보", "저장된 ID/PW가 없습니다.")
        }

        if (viewModel.checkStatus()) {
            viewModel.registerUser()
        } else {
            eventFail()
        }
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
                        etName.id -> SignUpViewModel.Field.NAME
                        etId.id -> SignUpViewModel.Field.ID
                        etPassword.id -> SignUpViewModel.Field.PASSWORD
                        etNumber.id -> {
                            if (phoneNumberRegex.containsMatchIn(editable.toString())) SignUpViewModel.Field.PHONE else null
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
