package com.android.hanple.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.hanple.R
import com.android.hanple.databinding.ActivitySignUpBinding
import com.android.hanple.utils.ConvertUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var auth: FirebaseAuth //FirebaseAuth를 가져옴

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 시간 변환 테스트 시작
        Log.d("MainActivity", ConvertUtils.unixTimeConverter(1717053033).toString())

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로 가기 아이콘 클릭 리스너 설정
        binding.ivBack.setOnClickListener {
            val loginIntent = Intent(this, LogInActivity::class.java)
            startActivity(loginIntent)
            finish() // 현재 액티비티를 종료
        }

        // SharedPreferences 초기화
//        val sharedPreference = getSharedPreferences("userInfo", MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = sharedPreference.edit()

        binding.btnSignup.setOnClickListener {
//            handleSignup(editor, sharedPreference)
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
    }

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
                            if (ConvertUtils.phoneNumberRegex.containsMatchIn(editable.toString())) SignUpViewModel.Field.PHONE else null //Unix 시간 변환, Regex 변환과 같은 단순 변환 코드는 ConvertUtils에 모아 두었어요!
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

    private fun signUp() {
        var email: String = binding.etId.text.toString()
        var password: String = binding.etPassword.text.toString()
        auth = FirebaseAuth.getInstance()
        //firebase-auth-ktx가 deprecated되어서, firebase-auth를 디펜던시에 추가했습니다.
        //firebase-auth에서는 java 코드처럼 getInstance()로 값을 가져오면 된다고 하네요.
        //https://firebase.google.com/docs/auth/android/start?hl=ko#java_1
        //위 공식 문서의 Kotlin+KTX 메소드가 먹히지 않으면 java 코드를 참고해 보세요.
        auth.createUserWithEmailAndPassword(email, password) //email, password 정보를 firebase에 넘기고
            .addOnCompleteListener(this@SignUpActivity) { task -> //task 자리에 값을 넘겨받음
                if (task.isSuccessful) { //파이어베이스 로그인 성공
                    val user = this.auth.currentUser
                    updateUI(user)
                } else { //로그인 실패
                    val exception = task.exception!!.toString()
                    if (exception.contains("FirebaseAuthUserCollisionException")) { //이미 등록된 이메일
                        updateUI(null)
                    }
                }

            }
    }

//    companion object {
//        val firebaseLoginSuccess: Toast = Toast.makeText(this@SignUpActivity, "파이어베이스 로그인 성공", Toast.LENGTH_SHORT)
//        val intentMain = Intent(this@SignUpActivity, MainActivity::class.java)
//    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {

        }
        else {

        }
    }
}
