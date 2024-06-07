package com.android.hanple.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.databinding.ActivityArchiveBinding

class ArchiveActivity : AppCompatActivity() { //검색 기록 또는 보관함을 보여줍니다.
    //검색 기록 및 보관함을 하단 탭으로 옮기고, 로그아웃 또는 탈퇴를 멀리 빼는 것은 어떨지...
    //디자인 피드백 때 물어봐야겠음. 우선은 Activity로 작성하겠습니다.
    private lateinit var binding: ActivityArchiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        } enableEdgeToEdge()부터 화면비 조절 코드라고 설명 받음! 그렇구나.

        val intent: Intent = intent //SettingsFragment에서 보낸 인텐트.
        val testText = intent.getStringExtra("key")
        binding.tvTest.text = testText

        clickBackIcon(binding)
    }
    private fun clickBackIcon(binding: ActivityArchiveBinding) {
        binding.ivBackIcon.setOnClickListener {
            finish()
        }
    }

}