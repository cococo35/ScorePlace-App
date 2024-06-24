package com.scoreplace.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivityPrivacyPolicyBinding
import com.scoreplace.hanple.ui.search.MainActivity

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    private val viewModel: PrivacyPolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Hanple) //앱 시작 스플래시
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //View -> ViewModel로 정보 전송
        binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
            val isAtBottom: Boolean
                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
            viewModel.updateScrolled(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
        }

        //ViewModel -> View 정보 받아오기
        viewModel.scrolled.observe(this) { isChecked -> //ViewModel에서
            binding.btnYes.isEnabled = isChecked
            changeButtonText(isChecked)
        }

        binding.btnYes.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
        private fun changeButtonText(isChecked: Boolean) { //텍스트는 color와 달리 selector 없어 여기 작성함.
        if (isChecked) {
            binding.btnYes.text = getString(R.string.privacy_policy_scrolled)
        } else binding.btnYes.text = getString(R.string.privacy_policy_unscrolled)
    }
}