package com.scoreplace.hanple.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {

    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = _binding!! //fragment 생명 주기를 고려
    private val viewModel: PrivacyPolicyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)

        //View -> ViewModel로 정보 전송
        binding.sv.setOnScrollChangeListener { _, _, _, _, _ ->
            val isAtBottom: Boolean
                    = binding.sv.scrollY + binding.sv.height >= binding.sv.getChildAt(0).measuredHeight
            viewModel.updateReadAll(isAtBottom) // 뷰모델로 스크롤 다 내렸는지 아닌지 체크
        }

        //ViewModel -> View 정보 받아오기
        viewModel.readAll.observe(viewLifecycleOwner) { isChecked -> //ViewModel에서
            binding.btnYes.isEnabled = isChecked
            changeButtonText(isChecked)
        }

        //View 내 처리사항
        binding.ivBack.setOnClickListener {
        }
        binding.btnYes.setOnClickListener {
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //fragment 생명 주기를 고려
    }



    private fun changeButtonText(isChecked: Boolean) { //텍스트는 color와 달리 selector 없어 여기 작성함.
        if (isChecked) {
            binding.btnYes.text = getString(R.string.privacy_policy_scrolled)
        } else binding.btnYes.text = getString(R.string.privacy_policy_unscrolled)
    }
}