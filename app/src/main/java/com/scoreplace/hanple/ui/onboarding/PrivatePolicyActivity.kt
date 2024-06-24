package com.scoreplace.hanple.ui.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivityPrivatePolicyBinding

class PrivatePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivatePolicyBinding
    private val viewModel: PrivatePolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Hanple) //앱 시작 스플래시
        binding = ActivityPrivatePolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cbReadAll.setOnCheckedChangeListener { _, isChecked -> //ViewModel에 체크박스 체크 여부 전송
            viewModel.updateReadAll(isChecked)
        }
        viewModel.readAll.observe(this) { isChecked -> //ViewModel에서
            binding.btnDismiss.isEnabled = isChecked
        }

        binding.btnDismiss.setOnClickListener {
            finish()
        }
    }
}