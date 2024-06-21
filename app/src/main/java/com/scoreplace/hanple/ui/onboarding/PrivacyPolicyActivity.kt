package com.scoreplace.hanple.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    private val viewModel: PrivacyPolicyViewModel by viewModels()
    private val intent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //View -> ViewModel로 정보 전송
        binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
            val isAtBottom: Boolean
                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
            viewModel.updateReadAll(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
        }

        //ViewModel -> View 정보 받아오기
        viewModel.readAll.observe(this) { isChecked -> //ViewModel에서
            binding.btnYes.isEnabled = isChecked
            changeButtonText(isChecked)
        }

        //View 내 처리사항
        binding.ivBack.setOnClickListener {
            intent.putExtra("", false)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.btnYes.setOnClickListener {
            intent.putExtra("", true)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun changeButtonText(isChecked: Boolean) { //텍스트는 color와 달리 selector 없어 여기 작성함.
        if (isChecked) {
            binding.btnYes.text = getString(R.string.privacy_policy_scrolled)
        } else binding.btnYes.text = getString(R.string.privacy_policy_unscrolled)
    }
}