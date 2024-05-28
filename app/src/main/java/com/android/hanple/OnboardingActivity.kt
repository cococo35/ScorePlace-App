package com.android.hanple

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.hanple.R
import com.android.hanple.databinding.ActivityOnboardingBinding
import com.android.hanple.databinding.FragmentSettingsBinding

class OnboardingActivity : AppCompatActivity() { //회원가입 및 로그인 페이지
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        while (loginState) {
//        앱 처음 설치 - 회원가입 플로우
//        현재 지금 로그인이 되어있는지 체크
//        로그인 true - 바로 MainAcivity로 넘어간다.
//        }
    }
}