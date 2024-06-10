package com.android.hanple.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.android.hanple.R
import com.android.hanple.Room.RecommendDataBase
import com.android.hanple.Room.RecommendPlace
import com.android.hanple.Room.recommendPlaceGoogleID
import com.android.hanple.databinding.ActivityLoginBinding
import com.android.hanple.ui.MainActivity
import com.android.hanple.utils.SharedPreferencesUtils
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class LogInActivity : AppCompatActivity() {

    companion object {
        lateinit var preferences: SharedPreferencesUtils
    }

    private lateinit var binding: ActivityLoginBinding // 뷰 바인딩 객체를 늦은 초기화로 선언
    private val loginViewModel: LogInViewModel by viewModels() // ViewModel 객체를 by viewModels()로 초기화
    private val recommendDAO by lazy {
        RecommendDataBase.getMyRecommendPlaceDataBase(this).getMyRecommendPlaceDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences =
            SharedPreferencesUtils(applicationContext) //다른 액티비티에서도 사용해야 하므로 context도 보내주기.
        super.onCreate(savedInstanceState)
        insertRoomData()
        //화면 SplashScreen 적용 및 바인딩
        installSplashScreen()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        //만약 SharedPreferences에 이메일 정보가 저장이 되어 있다면,
        val spfEmail = preferences.loadRememberMe() //없다면 "" 출력할 것으로 보임.
        binding.etId.setText(spfEmail) //정보가 저장되어 있다면 자동으로 작성됨.
        setContentView(binding.root)
        // 로그인 버튼 클릭 리스너 설정
        binding.btnLogin.setOnClickListener {
            // 입력된 사용자 아이디와 비밀번호를 가져옴
            val userId = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            //요 SharedPreferences도 결국엔 data layer에 저장된다면, SharedPref 관련 코드는 다 ViewModel로 가는 것이 맞을까? 헷갈린다.
            preferences.rememberMe(userId) //userId에 이메일 값 작성. 이 코드가 정상 실행된다면 앱을 껐다 켰을 때 로그인 성공한 값이 etId 텍스트 창에 떠 있어야 합니다.
            // 기획 의논이 필요: Email + Password 사용이 제일 예시가 많아서 우선 적용했어요. 얼마든지 다른 방식의 로그인도 추가 가능한 것으로 알고 있습니다. (google, facebook, 심지어는 github 계정 연동 등)
            // 휴대폰 인증이나 앱 자체에서 ID 관리하는 경우(UID와 다름)에는 조금 다룰 것이 많아지는 것 같습니다. 기능상으로 필요해 보인다면 더 알아볼게요!


            // ViewModel을 통해 사용자 유효성 검사 수행
            loginViewModel.validateUser(userId, password)

        }

        // ViewModel의 로그인 결과를 관찰하여 처리
        loginViewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userID", binding.etId.text.toString())
                startActivity(intent) // 로그인 성공 시, 메인 액티비티로 이동
                finish()
            } else {
                // 로그인 실패 시, 오류 메시지 표시
                Toast.makeText(this, R.string.ts_login_id, Toast.LENGTH_SHORT).show()
            }
        })

        // 회원가입 텍스트뷰 클릭 리스너 설정
        binding.tvSignup.setOnClickListener {
            val signupIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signupIntent)
        }
    }


    private fun insertRoomData() {
        runBlocking {
            for (i in 0..recommendPlaceGoogleID.size - 1) {
                recommendDAO.insertRecommendPlace(
                    RecommendPlace(
                        i + 1,
                        recommendPlaceGoogleID[i]
                    )
                )
            }
        }
    }
}