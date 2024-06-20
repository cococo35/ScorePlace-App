package com.android.hanple.ui.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.R
import com.android.hanple.databinding.ActivityPrivatePolicyBinding

class PrivatePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivatePolicyBinding
    private val viewModel: PrivatePolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivatePolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
            val isAtBottom: Boolean
                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
                viewModel.updateReadAll(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
        }

        viewModel.readAll.observe(this) { isChecked -> //ViewModel에서
            binding.btnDismiss.isEnabled = isChecked
        }

        viewModel.buttonText.observe(this) { text -> //ViewModel에서
            binding.btnDismiss.isEnabled = text
        }

        binding.btnDismiss.setOnClickListener {
            finish()
        }
    }
}